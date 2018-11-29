package grupo7.com.appg7;

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

import java.util.ArrayList;


public class Tab_Historial_Fragments extends Fragment {
    private BarChart barChart;
    private int[] pesos = { 90, 91, 93, 92, 92, 92, 92 };
    private String[] dias = {"L", "M", "M", "J","V", "S", "D"};
    private int[]colors=new int[]{Color.BLACK,Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.GRAY,Color.MAGENTA};
    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView recyclerHistorial;
    ArrayList<HistorialVo> listaHistorialDatosPeso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_tab_historial, container, false);

        listaHistorialDatosPeso = new ArrayList<>();
        recyclerHistorial = (RecyclerView) vista.findViewById(R.id.recyclerId);
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(getContext())); //usamos getContext en vez de .this porque no es una actividad

        llenarLista();

        AdapatadorHistorialPeso adapter = new AdapatadorHistorialPeso(listaHistorialDatosPeso);
        recyclerHistorial.setAdapter(adapter);

         barChart = (BarChart)vista.findViewById(R.id.BarChart);
        createCharts();

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

    private Chart getSameChart(Chart chart, String description, int textColor, int animateY ){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.animateY(animateY);

        return chart;
    }

       /* private void legend ( Chart chart ){
            Legend legend = chart.getLegend();
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

            ArrayList<LegendEntry> entries = new ArrayList<>();
            for( int i = 0; i < dias.length; i++ ){
                LegendEntry entry = new LegendEntry();
                entry.formColor = colors[i];
                entry.label = dias[i];
                entries.add(entry);
            }
            legend.setCustom(entries);
        }*/

    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i < pesos.length; i++)
            entries.add(new BarEntry(i, pesos[i]));
        return entries;
    }

    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setTextSize(13);
        axis.setValueFormatter(new IndexAxisValueFormatter(dias));
    }
    private void axisLeft(YAxis axis){
        axis.setSpaceTop(35);
        axis.setAxisMinimum(30);
        axis.setTextSize(13);
    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createCharts(){
        barChart = (BarChart)getSameChart(barChart, "Pesos", Color.RED, 3000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.invalidate();

        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12);
        return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet = (BarDataSet)getData(new BarDataSet(getBarEntries(), ""));

        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.65f);
        return barData;
    }

}