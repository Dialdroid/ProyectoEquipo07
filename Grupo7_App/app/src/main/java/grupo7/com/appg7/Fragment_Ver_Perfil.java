package grupo7.com.appg7;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bolts.Task;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;


public class Fragment_Ver_Perfil extends Fragment implements View.OnClickListener {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<Altura> alturaArrayList;
    TextView alturaUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View v = inflater.inflate(R.layout.fragment_fragment__ver__perfil, container, false);


        Button editButton = (Button)v.findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(this);


        final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        alturaUser = (TextView) v.findViewById(R.id.alturaUser);



        if (usuario != null) {
            String nombre_usario = usuario.getDisplayName();
            String email_usuario = usuario.getEmail();
            Uri foto_usuario = usuario.getPhotoUrl();

            loadDataFromFirestore();

            /*CollectionReference medidasInfo = db.collection("usuarios").document(usuario.getUid()).collection("Altura");
            medidasInfo.orderBy("Fecha", Query.Direction.DESCENDING).limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.get("Altura") != null) {

                                    double altura = documentSnapshot.getDouble("Altura");

                                    alturaUser.setText(Double.toString(altura));


                                }
                            }
                        }
                    });*/


            //int telefono = usuario.telephone();
            //String numero_usuario = usuario.getPhoneNumber();
            String fotoAuxiliar = "https://bit.ly/2zwrJ34";

            TextView nombreUser = (TextView) v.findViewById(R.id.nameUser);
            nombreUser.setText(nombre_usario);



            TextView emailUser = (TextView) v.findViewById(R.id.emailUser);
            emailUser.setText(email_usuario);
            ImageView photoUser = (ImageView) v.findViewById(R.id.photoUser);

            //Context c = getActivity().getApplicationContext();
            Picasso.with(getActivity()).load(foto_usuario).fit().into(photoUser);


            if (usuario.getPhotoUrl() == null) {
                Picasso.with(getContext()).load(fotoAuxiliar).into(photoUser);
            }


        }


        return v;


    }


    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.buttonEdit:
                fragment = new Fragment_Editar_Perfil();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedor, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadDataFromFirestore() {


        final CollectionReference medidasInfo = db.collection("usuarios").document(user.getUid()).collection("Altura");

        medidasInfo.orderBy("Altura", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                            Log.d(TAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());

                            Altura mimedida = new Altura(documentSnapshot.getDouble("Altura"));
                            alturaArrayList.add(mimedida);
                            String altura = Double.toString(mimedida.getAltura());
                            Log.d("alt", altura);
                            alturaUser.setText(altura);

                        }


                    }
                });

    }//



}

