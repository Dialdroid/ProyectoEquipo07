package grupo7.com.appg7;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ConnectionsClient client;
    private String usuarioActual;
    private Sensores sensores;
    private boolean resmagnetico;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting Android Things app...");


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



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
