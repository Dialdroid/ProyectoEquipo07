package grupo7.com.appg7;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;


public class PrincipalFragment extends Fragment {

    private LineChart chart;
    ArrayList<Entry> values = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DocumentReference userPeso = db.collection("sensores").document("medidaPeso");
    ArrayList<HistorialVo> pesoArrayList;




    public static PrincipalFragment newInstance() {
        PrincipalFragment fragment = new PrincipalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_peso, container, false);

        setUpDatos();


        pesoArrayList = new ArrayList<>();

           // // Chart Style // //
            chart = vista.findViewById(R.id.chart1);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .document(user.getUid())
                .collection("Peso") // Documento del usuario
                .orderBy("Fecha", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        values = new ArrayList<>();
                        int i = 0;
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("Peso") != null) {
                                double measure = doc.getDouble("Peso");
                                values.add(new Entry(i, Float.valueOf(String.valueOf(measure))));
                                i++;
                            }
                        }
                        //Log.d(TAG, "Current measures: " + measures.toArray()[0].toString());


                        LineDataSet dataSet = new LineDataSet(values, "Customized values");

                        chart.setBackgroundColor(Color.WHITE);

                        // disable description text
                        chart.getDescription().setEnabled(false);

                        // enable touch gestures
                        chart.setTouchEnabled(true);

                        // set listeners

                        chart.setDrawGridBackground(false);

                        // enable scaling and dragging
                        chart.setDragEnabled(true);
                        chart.setScaleEnabled(true);
                        chart.setScaleXEnabled(true);
                        chart.setScaleYEnabled(true);

                        // force pinch zoom along both axis
                        chart.setPinchZoom(true);
                        XAxis xAxis;
                        {   // // X-Axis Style // //
                            xAxis = chart.getXAxis();

                            // vertical grid lines
                            xAxis.enableGridDashedLine(10f, 10f, 0f);
                        }

                        YAxis yAxis;
                        {   // // Y-Axis Style // //
                            yAxis = chart.getAxisLeft();

                            // disable dual axis (only use LEFT axis)
                            chart.getAxisRight().setEnabled(false);

                            // horizontal grid lines
                            yAxis.enableGridDashedLine(10f, 10f, 0f);

                            // axis range
                            yAxis.setAxisMaximum(150f);
                            yAxis.setAxisMinimum(-50f);
                        }


                        // add limit lines


                        Legend l = chart.getLegend();
                        l.setEnabled(false);

                        // Setting Data
                        LineData data = new LineData(dataSet);
                        chart.setData(data);
                        chart.animateX(2500);
                        //refresh
                        chart.invalidate();

                    }});




        return vista;
    }



    private void setUpDatos() {

        db = FirebaseFirestore.getInstance();
    }






}



