package grupo7.com.appg7;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class NearbyAdvManager {
    private static final String TAG = "NearByManagerAdv";

    protected static final String SERVICE_ID = "grupo7.com.appg7";

    private Context ctx;
    private ConnectionsClient client;
    private EventListener listener;


    public NearbyAdvManager(Context ctx, EventListener listener) {
        Log.d(TAG, "Constructor..");
        this.ctx = ctx;
        this.listener = listener;
        client = Nearby.getConnectionsClient(ctx);
        client.startAdvertising("AndroidThings",
                SERVICE_ID,
                connectionLifeCycleCB,
                new AdvertisingOptions(Strategy.P2P_STAR))
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i(TAG, "OnSuccess...");
                            }
                        }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "OnFailure 1");
                        e.printStackTrace();
                    }
                });
    }


    private ConnectionLifecycleCallback connectionLifeCycleCB = new ConnectionLifecycleCallback() {
        @Override
        public void onConnectionInitiated(String s, ConnectionInfo connectionInfo) {
            Log.i(TAG, "Connection initiated. Endpont ["+s+"]");
            Log.i(TAG, "Incoming endpoint ["+connectionInfo.getEndpointName()+"]");
            Log.i(TAG, "Is incoming ["+connectionInfo.isIncomingConnection()+"]");
            // Let us accept the connection

            Nearby.getConnectionsClient(ctx)
                    .acceptConnection(s, payloadCallback );

        }

        @Override
        public void onConnectionResult(String s, ConnectionResolution connectionResolution) {
            Log.i(TAG, "Connection result. Endpont ["+s+"]");

        }

        @Override
        public void onDisconnected(String s) {
            Log.i(TAG, "Disconnected. Endpont ["+s+"]");


        };
    };


    private PayloadCallback payloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(String s, Payload payload) {
            Log.i(TAG, "Payload received");
            byte[] b = payload.asBytes();
            String content = new String(b);
            Log.i(TAG, "Content ["+content+"]");
            listener.onMessage(content);
        }

        @Override
        public void onPayloadTransferUpdate(String s, PayloadTransferUpdate payloadTransferUpdate) {
            Log.d(TAG, "Payload Transfer update ["+s+"]");
            if (payloadTransferUpdate.getStatus() == PayloadTransferUpdate.Status.SUCCESS) {

                Log.i(TAG, "Payload received from Endpoint ["+s+"]");
            }
        }
    };

    public interface EventListener {
        public void onMessage(String message);
    }
}
