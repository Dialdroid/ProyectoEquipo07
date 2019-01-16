package grupo7.com.appg7;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.comun.Mqtt.TAG;
import static com.example.comun.Mqtt.broker;
import static com.example.comun.Mqtt.clientId;
import static com.example.comun.Mqtt.qos;
import static com.example.comun.Mqtt.topicRoot;

public class MainActivity extends Activity implements MqttCallback {

    private static final String TAGG = MainActivity.class.getSimpleName();
    private ConnectionsClient client;
    private String usuarioActual;
    private Sensores sensores;
    private boolean resmagnetico;

    FirebaseFirestore db= FirebaseFirestore.getInstance();
    //FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    //DatabaseReference db = FirebaseDatabase.getInstance().getReference();


    private MqttClient Mqttclient;
    //private String enviarGas = "grupo7/practica/enviarGas/";

    public String fecha;
    public String gas;
    public String movimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(getBaseContext());


        Log.i(TAG, "Starting Android Things app...");
        //conectarse al broker   iot.eclipse.org
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
        //suscribirse al broker  iot.eclipse.org
        try {
            Log.i(TAG, "Suscrito a " + topicRoot + "#");
            Mqttclient.subscribe(topicRoot + "#", qos);
            Mqttclient.setCallback(this);
        } catch (MqttException e) {
            Log.e(TAG, "Error al suscribir.", e);
        }
        //publicar en el broker  iot.eclipse.org
        /*try {
            Log.i(TAG, "Publicando mensaje: " + "hola");
            MqttMessage message = new MqttMessage("hola".getBytes());
            message.setQos(qos);
            message.setRetained(false);
            Mqttclient.publish(enviarGas + "saludo", message);
        } catch (MqttException e) {
            Log.e(TAG, "Error al publicar.", e);
        }*/

//----------------------------
        //--UART--
        /*
        PeripheralManager perifericos = PeripheralManager.getInstance();
        Log.d("HomeActivity", "GPIO: " + perifericos.getGpioList());

        Log.i(TAGG, "Lista de UART disponibles: " + ArduinoUart.disponibles());



        final ArduinoUart uart = new ArduinoUart("MINIUART", 115200);
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e) {

            Log.w(TAGG, "Error en sleep()", e);
        }

        final String s = uart.leer();
        //final String s = "pepito";

        Log.d(TAGG, "Recibido de Arduino: " + s);

*/

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
    @Override public void messageArrived(final String topic, MqttMessage message)
            throws Exception {
        final String payload = new String(message.getPayload());


        runOnUiThread(new Runnable() {
            @Override
            public void run() {



                //FirebaseFirestore db = FirebaseFirestore.getInstance();
                //Log.i(TAG, "Recibiendo: " + topic + "->" + payload);
                if (topic.equals("grupo7/practica/enviarGas")){
                    Log.i(TAG, "Recibido del topic grupo7/practica/enviarGas: "+ payload);
                    gas  = payload;

                }

                if (topic.equals("grupo7/practica/enviarFecha")){
                    Log.i(TAG, "Recibido del topic grupo7/practica/enviarGas: "+ payload);
                    fecha = payload;
                }
                if (topic.equals("grupo7/practica/enviarMovimiento")){
                    Log.i(TAG, "Recibido del topic grupo7/practica/enviarMovimiento: "+ payload);
                    movimiento = payload;
                }

                //FirebaseFirestore.getInstance();


                Map<String, String> datos = new HashMap<>();
                datos.put("fecha",fecha );
                datos.put("gas", gas);
                db.collection("usuarios").document("E7lFmxiGkJWuPKr1GkvEuhdAAZg2").collection("Gas").add(datos);

                Map<String, String> datos1 = new HashMap<>();
                datos.put("fecha",fecha );
                datos.put("movimiento", movimiento);
                db.collection("usuarios").document("E7lFmxiGkJWuPKr1GkvEuhdAAZg2").collection("Gas").add(datos1);

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
