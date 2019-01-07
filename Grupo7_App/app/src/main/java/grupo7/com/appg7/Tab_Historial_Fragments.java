package grupo7.com.appg7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class Tab_Historial_Fragments extends Fragment {


    public static Tab_Historial_Fragments newInstance() {
        Tab_Historial_Fragments fragment = new Tab_Historial_Fragments();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private AlertDialog dialog;
    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView recyclerHistorial;
    ArrayList<HistorialVo> listaHistorialDatosPeso;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_historial, container, false);

        listaHistorialDatosPeso = new ArrayList<>();
        recyclerHistorial = (RecyclerView) vista.findViewById(R.id.recyclerId);
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(getContext())); //usamos getContext en vez de .this porque no es una actividad

        llenarLista();

        final AdapatadorHistorialPeso adapter = new AdapatadorHistorialPeso(listaHistorialDatosPeso);
        recyclerHistorial.setAdapter(adapter);

        Button buttonRemove = (Button) vista.findViewById(R.id.delete);
        Button buttonAdd = (Button) vista.findViewById(R.id.add);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.app.AlertDialog.Builder dialogo1 = new android.support.v7.app.AlertDialog.Builder(getContext());

                dialogo1.setTitle("Advertencia");
                dialogo1.setMessage("¿ Quiere borrar el ultimo peso?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        listaHistorialDatosPeso.remove(listaHistorialDatosPeso.size() - 1);
                        adapter.notifyItemRemoved(listaHistorialDatosPeso.size());
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.modal_layout, null);
                final EditText newPeso = (EditText) mView.findViewById(R.id.edit_peso);
                Button AddPeso = (Button) mView.findViewById(R.id.add_peso);
                AddPeso.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!newPeso.getText().toString().isEmpty()) {
                            int numero = Integer.parseInt(newPeso.getText().toString());

                            listaHistorialDatosPeso.add(listaHistorialDatosPeso.size(), new HistorialVo("Peso: " + numero + "kg", "Subtitulo nuevo titular", 1));
                            adapter.notifyItemInserted(listaHistorialDatosPeso.size());
                            Toast.makeText(getActivity(), "Peso añadido",
                                    Toast.LENGTH_LONG).show();
                            CloseDialog();

                        } else {
                            Toast.makeText(getActivity(), "Escribe tu nuevo peso",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });

        return vista;


    }

    private void llenarLista() {
        listaHistorialDatosPeso.add(new HistorialVo("Peso: 73kg", "Fecha: 10/10/2018", R.drawable.user_default));
        listaHistorialDatosPeso.add(new HistorialVo("Peso: 70kg", "Fecha: 10/24/2018", R.drawable.user_default));
        listaHistorialDatosPeso.add(new HistorialVo("Peso: 72kg", "Fecha: 11/12/2018", R.drawable.user_default));
        listaHistorialDatosPeso.add(new HistorialVo("Peso: 73kg", "Fecha: 11/14/2018", R.drawable.user_default));
        listaHistorialDatosPeso.add(new HistorialVo("Peso: 72kg", "Fecha: 11/17/2018", R.drawable.user_default));
        listaHistorialDatosPeso.add(new HistorialVo("Peso: 71kg", "Fecha: 11/19/2018", R.drawable.user_default));
    }

    private void CloseDialog() {
        if (dialog != null) dialog.dismiss();
    }
}