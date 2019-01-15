package grupo7.com.appg7;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Medida {

    private double peso;
    private double altura;
    private String fecha;
    private String imc;

    public Medida() { }

    public Medida(double peso, double altura, String fecha) {
        this.peso = peso;
        this.altura = altura;
        this.fecha = fecha;
        this.imc = getImc();
    }

    //------------------ PESO

    public double getPeso() { return peso; }

    public void setPeso(double peso) { this.peso = peso; }

    //------------------ ALTURA

    public double getAltura() { return altura; }

    public void setAltura(double altura) { this.altura = altura; }

    //------------------ FECHA

    public String getFecha() {

        return fecha;
    }

    //------------------ IMC

    public String getImc() {

        Medida mimedida = new Medida();

        NumberFormat formatter = new DecimalFormat("#0.00");
        double imcNonFormat = peso/(altura*altura);
        String imc = formatter.format(imcNonFormat);
        return imc;
    }

    public void setImc(String imc) { this.imc = imc; }

    //------------------

    public void guardarMedida(String userId, Medida medida) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference dondeGuardo = db.collection("usuarios").document(userId).collection("medidas");


        dondeGuardo.add(medida).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {


            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("MEDIDA", "==>DocumentSnapshot written with ID: " + documentReference.getId());

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MEDIDA", "Error adding document", e);
                    }
                });
    }

}
