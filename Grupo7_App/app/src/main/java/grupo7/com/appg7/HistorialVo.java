package grupo7.com.appg7;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HistorialVo {
   // FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    private double peso;
    private String fecha;


    //private Uri foto_usuario = usuario.getPhotoUrl();

    public HistorialVo(double peso, String fecha) {
        this.peso = peso;
        this.fecha = fecha;

    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String Fecha) {
        this.fecha = fecha;
    }


}
