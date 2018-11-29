package grupo7.com.appg7;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;


public class PrincipalFragment extends Fragment {


    public PrincipalFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_principal, container, false);
        CardView peso = (CardView) vista.findViewById(R.id.peso);
        CardView imc = (CardView) vista.findViewById(R.id.imc);
        CardView altura = (CardView) vista.findViewById(R.id.altura);


        peso.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, new Tab_Historial_Fragments());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return vista;
    }



}
