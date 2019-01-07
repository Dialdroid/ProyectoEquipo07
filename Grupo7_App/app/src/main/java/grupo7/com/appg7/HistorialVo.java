package grupo7.com.appg7;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HistorialVo {
   // FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    private String peso;
    private String info;
    private int foto;

    //private Uri foto_usuario = usuario.getPhotoUrl();

    public HistorialVo(){

    }

    public HistorialVo(String peso, String info, int foto) {
        this.peso = peso;
        this.info = info;
        this.foto = foto;

    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
