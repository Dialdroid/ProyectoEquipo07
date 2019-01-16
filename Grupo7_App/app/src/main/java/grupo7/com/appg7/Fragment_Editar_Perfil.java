package grupo7.com.appg7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Fragment_Editar_Perfil extends Fragment {

    private static final int PICK_IMAGE = 100;
    //Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final DocumentReference userinfo = db.collection("usuarios").document(usuario.getUid());



        View vista = inflater.inflate(R.layout.fragment_fragment__editar__perfil, container, false);

        //data.put("nombre", miusuario.getNombre());

        final TextView cambiar_nombre = (TextView) vista.findViewById(R.id.editName);
        final TextView cambiar_correo = (TextView) vista.findViewById(R.id.editEmail);


        final ImageView photo = (ImageView) vista.findViewById(R.id.photoUser);


        Uri foto_usuario = usuario.getPhotoUrl();


        String fotoAuxiliar = "https://bit.ly/2zwrJ34";

        Picasso.with(getActivity()).load(foto_usuario).fit().into(photo);


        if (usuario.getPhotoUrl() == null) {
            Picasso.with(getContext()).load(fotoAuxiliar).into(photo);
        }

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });





        Button buttonOK = (Button)vista.findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String nuevoNombre = cambiar_nombre.getText().toString();
                String nuevoCorreo = cambiar_correo.getText().toString();

                Toast toast1 = Toast.makeText(getApplicationContext(), "Datos modificados correctamente", Toast.LENGTH_SHORT);
                // entra en el onclick

                toast1.show();
                Map<String,Object> data = new HashMap<>();

                if (!nuevoNombre.equals("")) {

                    data.put("nombre", nuevoNombre);

                }



                if (!nuevoCorreo.equals("")) {

                    data.put("correo", nuevoCorreo);

                }




                userinfo.update(data);
            }


        });


        return vista;

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


}
