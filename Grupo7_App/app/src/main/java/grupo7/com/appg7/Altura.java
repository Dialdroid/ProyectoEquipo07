package grupo7.com.appg7;


import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Altura {
    // FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    private double altura;


    //private Uri foto_usuario = usuario.getPhotoUrl();

    public Altura(double altura) {
        this.altura = altura;

    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }



}
