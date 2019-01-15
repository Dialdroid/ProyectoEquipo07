package grupo7.com.appg7;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sensores {

    private String nombre;
    private int gas;
    private boolean magnetico;
    private boolean presencia;
    private String fecha;

    public Sensores() { }

    public Sensores(String nombre, boolean magnetico, boolean presencia, String fecha, int gas) {
        this.nombre = nombre;
        this.magnetico = magnetico;
        this.presencia = presencia;
        this.fecha = fecha;
        this.gas = gas;
    }

    public boolean isMagnetico() {
        return magnetico;
    }

    public void setMagnetico(boolean magnetico) {
        this.magnetico = magnetico;
    }

    public boolean isPresencia() {
        return presencia;
    }

    public void setPresencia(boolean presencia) {
        this.presencia = presencia;
    }

    public void setGas(int gas){
        this.gas  = gas;
    }

    public int getGas(int gas){
        return gas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    //------------------

    public void guardarSensores(String userId, Sensores habitacion) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference dondeGuardo = db.collection("sensores/medidaGas");




        dondeGuardo.add(habitacion).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {


            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.d("SENSORES", "==>DocumentSnapshot written with ID: " + documentReference.getId());

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("SENSORES", "Error adding document", e);
                    }
                });
    }

}
