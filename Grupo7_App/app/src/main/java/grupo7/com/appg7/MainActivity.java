package grupo7.com.appg7;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private Button AcercaDe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("coleccion").document("documento").addSnapshotListener(
                new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e){
                        if (e != null) {
                            Log.e("Firebase", "Error al leer", e);
                        } else if (snapshot == null || !snapshot.exists()) {
                            Log.e("Firebase", "Error: documento no encontrado ");
                        } else {
                            Log.e("Firestore", "datos:" + snapshot.getData());
                        }
                    }
                });
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Escribiendo en la base de datos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                FirebaseFirestore db = FirebaseFirestore.getInstance(); Map<String, Object> datos = new HashMap<>();

                datos.put("dato_2", 48);
                datos.put("numbero", 3.14159265);
                datos.put("fecha", new Date());
                datos.put("lista", Arrays.asList(1, 2, 3));
                datos.put("null", null);

                Map<String, Object> datosAnidados = new HashMap<>();
                datosAnidados.put("a", 5);
                datosAnidados.put("b", true);
                datos.put("objectExample", datosAnidados);
                db.collection("coleccion").document("documento").set(datos);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragments_Inicio()).commit();

        infoUsuario();

    }







    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement


        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }

        if (id == R.id.accion_perfil){
            lanzarMiPerfil(null);
            return true;
        }




        return super.onOptionsItemSelected(item);
    }

    public void lanzarAcercaDe(View view) {
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }

    public void lanzarMiPerfil(View v){
        Intent i = new Intent(this, PerfilActivity.class);
        startActivity(i);
    }

    public void lanzarPreferencias(View view){
        Intent i = new Intent(this, PreferenciasActivity.class);
        startActivity(i);
    }
    public void mostrarPreferencias(View view){
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String s = "música: " + pref.getBoolean("musica",true)
                +", gráficos: " + pref.getString("graficos","?");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }



    @SuppressLint("ResourceType")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_home) {

            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragments_Inicio()).commit();
        }
        if (id == R.id.nav_camera) {

            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragments01()).commit();

        }
        if (id == R.id.nav_gallery) {

            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragments02()).commit();

        }

         if(id == R.id.photoUser){
            fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentEditarPerfil()).commit();
        }




         if (id == R.id.nav_slideshow) {

             startActivity(new Intent(Intent.ACTION_DIAL,
                     Uri.parse("tel:" + "7221055575")));

        }
        if (id == R.id.nav_manage) {

            lanzarPreferencias(null);

        }

        if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }

        if (id == R.id.nav_send) {
        }

        if (id == R.id.nav_cerrar_sesión) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);

            dialogo1.setTitle("Advertencia");
            dialogo1.setMessage("¿ Quiere cerrar sesión ?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    aceptar();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    public void aceptar() {
        AuthUI.getInstance().signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        MainActivity.this.finish();
                    }
                });
    }

    public void infoUsuario(){
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        if(usuario != null){
            String nombre_usario = usuario.getDisplayName();
            String email_usuario = usuario.getEmail();
            Uri foto_usuario = usuario.getPhotoUrl();
            String numero_usuario = usuario.getPhoneNumber();

            String fotoAuxiliar = "https://bit.ly/2zwrJ34";

            NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
            View header = navigationView.inflateHeaderView(R.layout.nav_header_main);

            TextView usuario_nombre = (TextView)header.findViewById(R.id.nameUser);
            usuario_nombre.setText(nombre_usario);

            TextView usuario_email = (TextView)header.findViewById(R.id.emailUser);
            usuario_email.setText(email_usuario);

            ImageView usuario_foto = (ImageView)header.findViewById(R.id.photoUser);
            Picasso.with(this).load(foto_usuario).into(usuario_foto);

            if(usuario.getEmail() == null){
                usuario_email.setText(numero_usuario);
            }

            if(usuario.getPhotoUrl()== null){
                Picasso.with(this).load(fotoAuxiliar).into(usuario_foto);
            }

        }
    }
}