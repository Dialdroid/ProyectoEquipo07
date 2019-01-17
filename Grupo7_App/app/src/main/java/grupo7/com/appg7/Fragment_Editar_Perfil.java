package grupo7.com.appg7;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        final CollectionReference putAlt = db.collection("usuarios").document(usuario.getUid()).collection("Altura");



        final View vista = inflater.inflate(R.layout.fragment_fragment__editar__perfil, container, false);

        //data.put("nombre", miusuario.getNombre());

        final TextView cambiar_nombre = (TextView) vista.findViewById(R.id.editName);
        final TextView cambiar_correo = (TextView) vista.findViewById(R.id.editEmail);
        final ImageView photo = (ImageView) vista.findViewById(R.id.photoUser);
        final TextView cambiar_altura = (TextView) vista.findViewById(R.id.editAltura);




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
                String nuevoAltura = cambiar_altura.getText().toString();

                Toast toast1 = Toast.makeText(getApplicationContext(), "Datos modificados correctamente", Toast.LENGTH_SHORT);
                // entra en el onclick

                toast1.show();
                DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                Map<String,Object> data = new HashMap<>();
                Map<String,Object> setalt = new HashMap<>();
                if (!nuevoNombre.equals("")) {

                    data.put("nombre", nuevoNombre);

                }

                if (!nuevoCorreo.equals("")) {

                    data.put("correo", nuevoCorreo);

                }

                if (!nuevoAltura.equals("")) {

                    setalt.put("Altura", nuevoAltura);
                    setalt.put("Fecha",date);


                }


                putAlt.add(setalt);
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
