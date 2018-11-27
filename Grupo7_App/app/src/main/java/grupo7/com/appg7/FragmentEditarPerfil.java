package grupo7.com.appg7;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class FragmentEditarPerfil extends Fragment {

    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View vista = inflater.inflate(R.layout.fragment_fragment_editar_perfil, container, false);


        // Inflate the layout for this fragment
        return vista;
    }

 /*   public void lanzarEditarPerfil(View view) {
        Intent i = new Intent(view.getContext(), EditarPerfil.class);
        startActivity(i);
    }

*/
}