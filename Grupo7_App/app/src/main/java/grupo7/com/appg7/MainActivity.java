package grupo7.com.appg7;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private Button AcercaDe;

        private static final String TAG = "MainActivity";

    private static final String TAGG = "NearbyApp";
    private NearbyDsvManager dsvManager;
    private boolean conectado = false;


    private static final String[] REQUIRED_PERMISSIONS =
            new String[] {
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };

    private static int REQUEST_CODE_REQUIRED_PERMISSIONS = 1;

    private static boolean permisosApp;



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
                            Log.e("Firebase", "Error", e);
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

        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


           @Override
            public void onClick(View view) {
                Snackbar.make(view, "Escribiendo en la base de datos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                FirebaseFirestore db = FirebaseFirestore.getInstance(); Map<String, Object> datos = new HashMap<>();

                datos.put("correo", 48);
                datos.put("iniciosesion", 3.14159265);
                datos.put("nombre", new Date());


                Map<String, Object> datosAnidados = new HashMap<>();
                datosAnidados.put("a", 5);
                datosAnidados.put("b", true);
                datos.put("objectExample", datosAnidados);
                db.collection("coleccion").document("documento").set(datos);

         }
        });*/

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragments_Inicio()).commit();

        infoUsuario();


        // Acceder a Datos de Perfil mediante la imagen del perfil del navigation drawable
        View headerview = navigationView.getHeaderView(0);
        ImageView fotoPerfil = (ImageView) headerview.findViewById(R.id.photoUser);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment_Ver_Perfil()).commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        sincronizarDispositivos(null);
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

    public void lanzarSonoff(View v){
        Intent i = new Intent(this, SonoffActivity.class);
        startActivity(i);
    }

    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }




    /*public void actualizarInfoUsuario() {

        final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        //-----------------------

        final String email_usuario = usuario.getEmail();
        final Uri foto_usuario = usuario.getPhotoUrl();




        final String telefono_usuario = usuario.getPhoneNumber();


        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View hView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference userInfo = db.collection("usuarios").document(usuario.getUid());

        if (usuario.getEmail() != null) {


            userInfo.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                        Log.w(TAG, "Medidas no disponibles", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {

                        Usuario miusuario = snapshot.toObject(Usuario.class);
                        TextView nav_user = (TextView) hView.findViewById(R.id.editText);
                        String nombre_miusuario = miusuario.getNombre();


                        nav_user.setText(nombre_miusuario);

                    } else {

                        Log.d(TAG, "Current data: null");
                    }
                }
            });

            TextView nav_email = (TextView) hView.findViewById(R.id.editText2);
            nav_email.setText(email_usuario);
        }

        if (foto_usuario != null) {

            ImageView nav_foto = (ImageView) hView.findViewById(R.id.photoUser);
            Picasso.with(this).load(foto_usuario).into(nav_foto);

        }

        if (usuario.getPhoneNumber() != null) {

            final String correo_null = "Correo no asignado";


            userInfo.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                        Log.w(TAG, "Medidas no disponibles", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {

                        Usuario miusuario = snapshot.toObject(Usuario.class);
                        TextView nav_user = (TextView) hView.findViewById(R.id.editText);
                        TextView nav_email = (TextView) hView.findViewById(R.id.editText2);
                        String nombre_miusuario = miusuario.getNombre();
                        String correo_miusuario = miusuario.getCorreo();




                        if (nombre_miusuario != null) {

                            nav_user.setText(nombre_miusuario);

                        } else {

                            nav_user.setText(telefono_usuario);

                        }

                        if (correo_miusuario != null) {

                            nav_email.setText(correo_miusuario);

                        } else {

                            nav_email.setText(correo_null);

                        }

                    } else {

                        Log.d(TAG, "Current data: null");
                    }
                }
            });
        }

    }//actualizarInfo()*/



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

            lanzarSonoff(null);
           // fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragments01()).commit();

        }
        if (id == R.id.nav_gallery) {

            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragments02()).commit();

        }

         if(id == R.id.photoUser){
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment_Ver_Perfil()).commit();
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

        if (id == R.id.chat) {

            startNewActivity(this,"com.example.h4chiko.chataplication");



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

            FirebaseFirestore db1 = FirebaseFirestore.getInstance();




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

    /*public void lanzarEditarPerfil(View view) {
        Intent i = new Intent(this, EditarPerfil.class);
        startActivity(i);
    }*/

    public void sincronizarDispositivos(View view) {

        if(permisosApp == true){

            if( conectado != true ) {

                dsvManager = new NearbyDsvManager(this,listener);

            } else {



            }

        }


    }

    @Override
    protected void onStart() {

        super.onStart();

    }

    private NearbyDsvManager.EventListener listener = new NearbyDsvManager.EventListener() {

        @Override
        public void startDiscovering() {


        }

        @Override
        public void onDiscovered() {

        }

        @Override
        public void onConnected() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            dsvManager.sendData(uid);
        }


    };



}