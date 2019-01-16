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

import grupo7.com.appg7.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;


    public class Tab_Sensores_Fragments extends Fragment {

         Double peso;
         Double altura;
        ImageView sobrepeso;
        ImageView normal;
        ImageView delgado;
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

            normal.setVisibility(v.GONE);
            delgado.setVisibility(v.GONE);
            sobrepeso.setVisibility(v.GONE);




            setIMC();
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
                                    }
                                    if(imc>18.5 && imc<24.9){
                                        normal.setVisibility(View.VISIBLE);
                                    }
                                    if(imc>=24.9){
                                        sobrepeso.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                        }
                    });




        }

        }
