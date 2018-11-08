package grupo7.com.appg7;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class PerfilActivity extends AppCompatActivity {


    private TextView Nombre;
    private TextView Correo;
    private ImageView Imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        String fotoAuxiliar = "https://bit.ly/2zwrJ34";
        Uri foto_usuario = usuario.getPhotoUrl();
        Nombre = (TextView) findViewById(R.id.nombre);

        Nombre.setText(usuario.getDisplayName());

        Correo = (TextView) findViewById(R.id.correo);

        if(usuario.getEmail() != null){
        Correo.setText(usuario.getEmail());}

        Imagen = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(foto_usuario).into(Imagen);

        if(usuario.getPhotoUrl()== null){
            Picasso.with(this).load(fotoAuxiliar).into(Imagen);
        }
    }
}
