package grupo7.com.appg7;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Usuario {

    private String nombre;
    private String correo;
    private long inicioSesion;

    public Usuario () {}

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setInicioSesion(long inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public long getInicioSesion() {
        return inicioSesion;
    }

    public Usuario (String nombre, String correo, long inicioSesion) {

        this.nombre = nombre;

        this.correo = correo;
        this.inicioSesion = inicioSesion;
    }

    public Usuario (String nombre, String correo) {

        this(nombre, correo, System.currentTimeMillis());
    }

    public static class Usuarios {
        static void guardarUsuario(final FirebaseUser user) {
            Usuario usuario = new Usuario(user.getDisplayName(),user.getEmail());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("usuarios").document(user.getUid()).set(usuario); }
    }

}