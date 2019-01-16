package grupo7.com.appg7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.example.comun.Mqtt.broker;
import static com.example.comun.Mqtt.clientId;
import static com.example.comun.Mqtt.qos;
import static com.example.comun.Mqtt.topicRoot;
import static com.example.comun.Mqtt.TAG;


public class SonoffActivity extends Activity implements MqttCallback {
    private MqttClient client;
    private String Toggle = "grupo7/practica/cmnd/power";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView medida_gas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonoff_main);


            medida_gas=(TextView)findViewById(R.id.dato_gas);
        Button onOff = (Button) findViewById(R.id.onoff);

        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.i(TAG, "Conectando al broker " + broker);
                    client = new MqttClient(broker, clientId, new MemoryPersistence());
                    MqttConnectOptions connOpts = new MqttConnectOptions();
                    connOpts.setCleanSession(true);
                    connOpts.setKeepAliveInterval(60);
                    connOpts.setWill(topicRoot+"WillTopic", "App desconectada".getBytes(),
                            qos, false);
                    client.connect(connOpts);
                } catch (MqttException e) {
                    Log.e(TAG, "Error al conectar.", e);
                }

                try {
                    Log.i(TAG, "Suscrito a " + topicRoot+"POWER");
                    client.subscribe(topicRoot+"POWER", qos);
                    client.setCallback(SonoffActivity.this);
                } catch (MqttException e) {
                    Log.e(TAG, "Error al suscribir.", e);
                }

                try {
                    Log.i(TAG, "Publicando mensaje: " + "Toggle");
                    MqttMessage message = new MqttMessage("Toggle".getBytes());
                    message.setQos(qos);
                    message.setRetained(false);
                    client.publish(Toggle , message);

                } catch (MqttException e) {
                    Log.e(TAG, "Error al publicar.", e);
                }



            }
        });


        Button arrancar = (Button) findViewById(R.id.enc_serv);

        arrancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startService(new Intent(SonoffActivity.this,
                        ServicioMedidas.class));
                final CollectionReference medidasInfoA = db.collection("usuarios").document(user.getUid()).collection("Gas");
                medidasInfoA.limit(1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    if (documentSnapshot.get("Gas") != null) {


                                        medida_gas.setText(documentSnapshot.getString("Gas"));
                                    }
                                }
                            }
                        });

            }
        });

        Button detener = (Button) findViewById(R.id.ap_serv);
        detener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopService(new Intent(SonoffActivity.this,
                        ServicioMedidas.class));

            }
        });


    }//--------------onCreate
    @Override public void onDestroy() {
      /*  try {
            Log.i(TAG, "Desconectado");
            client.disconnect();
        } catch (MqttException e) {
            Log.e(TAG, "Error al desconectar.", e);
        }*/
        stopService(new Intent(this, ServicioMedidas.class));
        super.onDestroy();
    } //----------onDestroy
    @Override public void connectionLost(Throwable cause) {
        Log.d(TAG, "Conexión perdida");

    }
    @Override public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(TAG, "Entrega completa");
    }

    @Override public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        final String payload = new String(message.getPayload());
        Log.d(TAG, "Recibiendo: " + topic + "->" + payload);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView a = (TextView) findViewById(R.id.msg);
                a.setText(payload);
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, ServicioMedidas.class));
    }
}