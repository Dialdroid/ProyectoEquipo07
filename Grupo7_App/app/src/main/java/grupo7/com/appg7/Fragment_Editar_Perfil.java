package grupo7.com.appg7;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Fragment_Editar_Perfil extends Fragment {


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




        Button buttonOK = (Button)vista.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                String nuevoNombre = cambiar_nombre.getText().toString();
                String nuevoCorreo = cambiar_correo.getText().toString();

                Toast toast1 = Toast.makeText(getApplicationContext(), "Toast por defecto", Toast.LENGTH_SHORT);
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


}
