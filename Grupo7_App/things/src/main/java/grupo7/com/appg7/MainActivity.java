package grupo7.com.appg7;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.comun.Mqtt.TAG;
import static com.example.comun.Mqtt.broker;
import static com.example.comun.Mqtt.clientId;
import static com.example.comun.Mqtt.qos;
import static com.example.comun.Mqtt.topicRoot;

public class MainActivity extends Activity implements MqttCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ConnectionsClient client;
    private String usuarioActual;
    private Sensores sensores;
    private boolean resmagnetico;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MqttClient Mqttclient;
    private String enviarGas = "grupo7/practica/Gas/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting Android Things app...");
        try {
            Log.i(TAG, "Conectando al broker " + broker);
            Mqttclient = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(60);
            connOpts.setWill(topicRoot + "WillTopic", "App desconectada".getBytes(),
                    qos, false);

            Mqttclient.connect();
        } catch (MqttException e) {
            Log.e(TAG, "Error al conectar.", e);
        }
        try {
            Log.i(TAG, "Suscrito a " + topicRoot + "POWER");
            Mqttclient.subscribe(topicRoot + "POWER", qos);
            Mqttclient.setCallback(this);
        } catch (MqttException e) {
            Log.e(TAG, "Error al suscribir.", e);
        }
        try {
            Log.i(TAG, "Publicando mensaje: " + "hola");
            MqttMessage message = new MqttMessage("hola".getBytes());
            message.setQos(qos);
            message.setRetained(false);
            Mqttclient.publish(enviarGas + "saludo", message);
        } catch (MqttException e) {
            Log.e(TAG, "Error al publicar.", e);
        }

//----------------------------
        //--UART--
        PeripheralManager perifericos = PeripheralManager.getInstance();
        Log.d("HomeActivity", "GPIO: " + perifericos.getGpioList());

        Log.i(TAG, "Lista de UART disponibles: " + ArduinoUart.disponibles());



        final ArduinoUart uart = new ArduinoUart("MINIUART", 115200);

        try {

            Thread.sleep(5000);
        } catch (InterruptedException e) {

            Log.w(TAG, "Error en sleep()", e);
        }

        final String s = uart.leer();
        
        Log.d(TAG, "Recibido de Arduino: " + s);



    }
    //-----------------------
    @Override public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(TAG, "Entrega completa");
    }
    @Override public void connectionLost(Throwable cause) {
        Log.d(TAG, "Conexión perdida");
    }
    @Override public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        final String payload = new String(message.getPayload());
        Log.d(TAG, "Recibiendo: " + topic + "->" + payload);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
