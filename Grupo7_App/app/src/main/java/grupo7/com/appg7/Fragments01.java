    package grupo7.com.appg7;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import grupo7.com.appg7.R;


    public class Fragments01 extends Fragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_fragments01, container, false);
        Button b = (Button) v.findViewById(R.id.button3);
        b.setOnClickListener(this);
        return v;
    }

        @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button3:
                        Fragments02 dos = new Fragments02();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.contenedor, dos ); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                        break;
                }
        }
    }
