    package grupo7.com.appg7;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import grupo7.com.appg7.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;


    public class Tab_Sensores_Fragments extends Fragment {

         Double peso;
         Double altura;
        ImageView sobrepeso;
        ImageView normal;
        ImageView delgado;
        TextView desc_d;
        TextView desc_n;
        TextView desc_s;
        TextView categoriaIMC;
        TextView inicio;
        TextView actual;
        TextView media;
        TextView medidas;
        ArrayList<Double> listMedia;

        ArrayList<Double> values = new ArrayList<>();
        int i=0;
        public static Tab_Sensores_Fragments newInstance() {
            Tab_Sensores_Fragments fragment = new Tab_Sensores_Fragments();
            return fragment;
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_estadisticas, container, false);

             normal = (ImageView) v.findViewById(R.id.normal);
             delgado = (ImageView) v.findViewById(R.id.delgado);
             sobrepeso = (ImageView) v.findViewById(R.id.sobrepeso);
             desc_s = v.findViewById(R.id.descSobrepeso);
            desc_d = v.findViewById(R.id.DescDelgado);
            desc_n = v.findViewById(R.id.DescNormal);
            categoriaIMC= v.findViewById(R.id.categoriaIMC);
            inicio= v.findViewById(R.id.peso_inicio);
            actual= v.findViewById(R.id.peso_actual);
            media= v.findViewById(R.id.media);
            medidas= v.findViewById(R.id.medidas);


            normal.setVisibility(v.GONE);
            delgado.setVisibility(v.GONE);
            sobrepeso.setVisibility(v.GONE);
            desc_d.setVisibility(v.GONE);
            desc_n.setVisibility(v.GONE);
            desc_s.setVisibility(v.GONE);



            setIMC();
            SetOthers();


            return v;

        }


        public void setIMC(){

            final CollectionReference medidasInfo = db.collection("usuarios").document(user.getUid()).collection("Peso");
            medidasInfo.orderBy("Fecha", Query.Direction.DESCENDING).limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.get("Peso") != null) {

                                    peso = documentSnapshot.getDouble("Peso");
                                    double imc = peso*(1.88*1.88);
                                    if(imc<=18.5){
                                        delgado.setVisibility(View.VISIBLE);
                                        desc_d.setVisibility(View.VISIBLE);
                                        categoriaIMC.setText("Peso insuficiente");

                                    }
                                    if(imc>18.5 && imc<24.9){
                                        normal.setVisibility(View.VISIBLE);
                                        desc_n.setVisibility(View.VISIBLE);
                                        categoriaIMC.setText("Normopeso");
                                    }
                                    if(imc>=24.9){
                                        sobrepeso.setVisibility(View.VISIBLE);
                                        desc_s.setVisibility(View.VISIBLE);
                                        categoriaIMC.setText("Sobrepeso grado I\n");

                                        if(imc>27 && imc<29.9){
                                            categoriaIMC.setText("Sobrepeso grado II (preobesidad)");
                                        }
                                        if(imc>30 && imc<34.5){
                                            categoriaIMC.setText("Obesidad de tipo I");
                                        }
                                        if(imc>35 && imc<39.9){
                                            categoriaIMC.setText("Obesidad de tipo II");
                                        }
                                        if(imc>40 && imc<49.9){
                                            categoriaIMC.setText("Obesidad de tipo III (mórbida)");
                                        }
                                        if(imc>50){
                                            categoriaIMC.setText("Obesidad de tipo IV (extrema)");
                                        }

                                    }

                                }
                            }
                        }
                    });




        }


        public void SetOthers(){
            final CollectionReference medidasInfoI = db.collection("usuarios").document(user.getUid()).collection("Peso");
            medidasInfoI.orderBy("Fecha", Query.Direction.ASCENDING).limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.get("Peso") != null) {

                                    peso = documentSnapshot.getDouble("Peso");
                                    inicio.setText(Double.toString(peso));
                                }
                            }
                        }
                    });

            final CollectionReference medidasInfoA = db.collection("usuarios").document(user.getUid()).collection("Peso");
            medidasInfoA.orderBy("Fecha", Query.Direction.DESCENDING).limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.get("Peso") != null) {

                                    peso = documentSnapshot.getDouble("Peso");
                                    actual.setText(Double.toString(peso));
                                }
                            }
                        }
                    });

            final CollectionReference medidasInfoM = db.collection("usuarios").document(user.getUid()).collection("Peso");
            medidasInfoM.orderBy("Fecha", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            listMedia = new ArrayList<>();
                            int i=0;
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                    if((documentSnapshot.get("Peso") != null)){
                                        i=i+1;
                                        listMedia.add(documentSnapshot.getDouble("Peso"));
                                        }
                                medidas.setText(Integer.toString(listMedia.size()));

                            }
                        }
                    });

            final CollectionReference medidasInfoMe = db.collection("usuarios").document(user.getUid()).collection("Peso");
            medidasInfoMe.orderBy("Fecha", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                peso = documentSnapshot.getDouble("Peso");
                                listMedia.add(peso);
                            }

                            double resu=Media(listMedia);
                            media.setText(Double.toString(resu));
                        }
                    });
        }

        public double Media(List<Double> list){
            double media=0;
            double suma=0;
            int i=0;
            for (int d=0;d<list.size();d++){
                i++;
               suma=list.get(d)+suma;
            }

            media=suma/i;

            return media;
        }

        }
