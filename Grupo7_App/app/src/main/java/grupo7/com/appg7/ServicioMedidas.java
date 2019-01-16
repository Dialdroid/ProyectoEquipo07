package grupo7.com.appg7;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ServicioMedidas extends Service {

    private NotificationManager notificationManager;
    static final String CANAL_ID = "mi_canal";
    static final int NOTIFICACION_ID = 1;


    @Override public void onCreate() {
        Toast.makeText(this,"Servicio creado",
                Toast.LENGTH_SHORT).show();



    }
    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis Notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 100, 300, 100});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificacion =
                new NotificationCompat.Builder(this, CANAL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Recogiendo medidas")
                        .setContentText("Recogiendo las medidas de los sensores(Sonoff:On)");

        PendingIntent intencionPendiente = PendingIntent.getActivity(
                this, 0, new Intent(this, SonoffActivity.class), 0);
        notificacion.setContentIntent(intencionPendiente);

        startForeground(NOTIFICACION_ID, notificacion.build());
        Toast.makeText(this,"Servicio arrancado "+ idArranque,
                Toast.LENGTH_SHORT).show();





        return START_STICKY;
    }
    @Override public void onDestroy() {
        Toast.makeText(this,"Servicio detenido",
                Toast.LENGTH_SHORT).show();
        notificationManager.cancel(NOTIFICACION_ID);

    }
    @Override public IBinder onBind(Intent intencion) {
        return null;
    }
}