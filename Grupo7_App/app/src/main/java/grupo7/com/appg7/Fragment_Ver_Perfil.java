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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;


public class Fragment_Ver_Perfil extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_fragment__ver__perfil, container, false);


        Button editButton = (Button)v.findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(this);


        final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();


        if (usuario != null) {
            String nombre_usario = usuario.getDisplayName();
            String email_usuario = usuario.getEmail();
            Uri foto_usuario = usuario.getPhotoUrl();
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
}

