    package grupo7.com.appg7;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;


    public class Tab_Historial_Fragments extends Fragment {
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
    }
