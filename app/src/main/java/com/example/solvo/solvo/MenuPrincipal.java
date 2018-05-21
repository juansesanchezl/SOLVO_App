package com.example.solvo.solvo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SQLib.Comentario;
import com.SQLib.ConsultasDB;
import com.SQLib.DatabaseHelper;
import com.SQLib.Establecimiento;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.dynamodb.ExceptionHandler;
import com.solvo.awsandroid.AWSLoginModel;
import com.SQLib.ConductorSolvo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AWSLoginModel awsLoginModel;
    Context contextMenu;
    public static DatabaseHelper db;
    public static List<Establecimiento> estableList = new ArrayList<>();
    public static List<Establecimiento> Alojamientos = new ArrayList<>();
    public static List<Establecimiento> Restaurantes = new ArrayList<>();
    public static List<Establecimiento> Parqueaderos = new ArrayList<>();
    public static List<Establecimiento> EstServicio = new ArrayList<>();
    public static List<Establecimiento> Peajes = new ArrayList<>();
    public static List<Establecimiento> Talleres = new ArrayList<>();
    public static boolean listaEstaLlena = false;
    public static double Kilometros_Radio = 100;
    public static List<Comentario> listaComentarios = new ArrayList<>();
    public static ConductorSolvo conductorActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_prin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextMenu = getApplicationContext();
        //showDialog();


        String who = AWSLoginModel.getSavedUserName(MenuPrincipal.this);
        System.out.println("WHOO-->"+who);
        ConsultasDB.obtenerConducSolvo(contextMenu,who);
        estableList.clear();
        db = new DatabaseHelper(this);
        if(db.getEstablecimientos().size()>0){
            //estableList.addAll(db.getEstablecimientos());
            //notifyUser(estableList.size()+"--ESTABLECIMIENTOS CARGADOS");
            listaEstaLlena = true;
        }else{
            System.out.println("ENTROOOO A DATABASE HELPER");

            ConsultasDB.obtenerEstabl(MenuPrincipal.this, db);
        }
        /*if(estableList.size() == 0){
            System.out.println("ENTROOOO A DATABASE HELPER");
            ConsultasDB.obtenerEstabl(MenuPrincipal.this, db);
            //estableList.addAll(db.getEstablecimientos());
        }else{
            notifyUser(estableList.size()+"--ESTABLECIMIENTOS CARGADOS");
            listaEstaLlena = true;
        }*/
        ConsultasDB.obtenerComent(contextMenu);
        imprimirLista(estableList);
        int idcomen = listaComentarios.size() + 1 ;
        String IDCOM = ""+ idcomen;
        //ConsultasDB.obtenCantidadComent(contextMenu,"ESTE ES UN COMENTARIO COOLS","juansesanchezl","E04");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*BOTONES*/
        /*Button btnRestaurante = (Button) findViewById(R.id.btnRestaurante);
        btnRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Restaurante.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        Button btnParqueadero = (Button) findViewById(R.id.btnParqueadero);
        btnParqueadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Parqueadero.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        Button btnAlojamiento = (Button) findViewById(R.id.btnAlojamiento);
        btnAlojamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Alojamiento.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        Button btnEstServicio = (Button) findViewById(R.id.btnEstServicio);
        btnEstServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, EstServicio.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        Button btnPeaje = (Button) findViewById(R.id.btnPeaje);
        btnPeaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MenuPrincipal.this, "ESTA FUNCIÓN SE ENCUENTRA EN CONSTRUCCIÓN", Toast.LENGTH_LONG).show();
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Peaje.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        Button btnTaller = (Button) findViewById(R.id.btnTaller);
        btnTaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Taller.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });*/
        CardView cardRestaurante = (CardView) findViewById(R.id.cardRestaurante);
        cardRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Restaurante.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        CardView cardParqueadero = (CardView) findViewById(R.id.cardParqueadero);
        cardParqueadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Parqueadero.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        CardView cardAlojamiento = (CardView) findViewById(R.id.cardAlojamiento);
        cardAlojamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Alojamiento.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        CardView cardEstServicio = (CardView) findViewById(R.id.cardEstServicio);
        cardEstServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, EstServicio.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        CardView cardPeaje = (CardView) findViewById(R.id.cardPeaje);
        cardPeaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Peaje.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        CardView cardTaller = (CardView) findViewById(R.id.cardTaller);
        cardTaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Taller.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

    }

    /*public void showDialog(){
        Dialog dialog = new Dialog(MenuPrincipal.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alerta_posicion_design);
        //dialog.setTitle("ALERTA");
        dialog.show();
    }*/

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
        getMenuInflater().inflate(R.menu.menu_prin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Toast.makeText(MenuPrincipal.this, "ESTA FUNCIÓN SE ENCUENTRA EN CONSTRUCCIÓN", Toast.LENGTH_LONG).show();
            MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, Configuracion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_SignOut) {
            cerrarSesionAction();
        }else if (id == R.id.nav_PerfilUSolvo) {
            // Handle the camera action
            perfilUSolvo();
        } else if (id == R.id.nav_PuntosSolvo) {
            puntosSolvo();
        } else if (id == R.id.nav_Instagram) {
            Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://www.instagram.com/solvotg/"));
            this.startActivity(intent);
        } else if (id == R.id.nav_Twitter) {
            Intent intent = null;
            try {
                // get the Twitter app if possible
                this.getPackageManager().getPackageInfo("com.twitter.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("twitter://user?user_id=924786592212013058"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Twitter app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://twitter.com/solvotg"));
            }
            this.startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void perfilUSolvo(){
        MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, perfilUSolvo.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    private void puntosSolvo(){
        MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, puntosSolvo.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    @Override
    protected void onResume() {
        super.onResume();

        String who = AWSLoginModel.getSavedUserName(MenuPrincipal.this);
        String mail = AWSLoginModel.getSavedUserEmail(MenuPrincipal.this);
        TextView hello = findViewById(R.id.TituloE);
        NavigationView navigationView =  (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView navImage = (ImageView) headerView.findViewById(R.id.Userimage);
        TextView navUsername = (TextView) headerView.findViewById(R.id.UserName);
        TextView navMail = (TextView) headerView.findViewById(R.id.UserMail);
        //navImage.setImageDrawable(R.drawable.ic_user);
        navUsername.setText(who);
        navMail.setText(mail);

        hello.setText("Bienvenido " + who + "!");
        cambiarEstado(who,"ACTIVO");

    }

    @Override
    protected void onPause() {
        super.onPause();

        String who = AWSLoginModel.getSavedUserName(MenuPrincipal.this);
        cambiarEstado(who,"INACTIVO");

    }

    private void cerrarSesionAction(){
        System.out.println("VA A CERRAR SESIÓN");
        estableList.clear();
        //FileUtils.deleteQuietly(MenuPrincipal.this.getCacheDir());
        try {
            AWSMobileClient.getInstance().initialize(MenuPrincipal.this, new AWSStartupHandler() {
                @Override
                public void onComplete(AWSStartupResult awsStartupResult) {
                    try {
                        IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
                        finish();
                        identityManager.signOut();
                        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(MenuPrincipal.this));

                    } catch (NullPointerException n) {
                        System.out.println("EXP-Null: " + n.getStackTrace());
                    }
                }
            }).execute();
            String who = AWSLoginModel.getSavedUserName(MenuPrincipal.this);
            cambiarEstado(who, "INACTIVO");

            MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, PagPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } catch (AmazonServiceException ase){
            System.out.println("ASE ERROR--"+ase.toString());
        } catch (Exception e){
            System.out.println("Error---"+e);
        }


    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void cambiarEstado(String user, String estado){

        ConsultasDB.cambiarEstado(MenuPrincipal.this,user,estado);
    }

    public static void imprimirLista(List<Establecimiento> establecimientos){
        Alojamientos.clear();
        Restaurantes.clear();
        Peajes.clear();
        EstServicio.clear();
        Parqueaderos.clear();
        Talleres.clear();

        if(listaEstaLlena){
            //notifyUser("SE IMPRIMIO LA LISTA");
            System.out.println("SE IMPRIMIO LA LISTA");
            for (Establecimiento e: establecimientos) {
                System.out.println("IDEST:"+e.getIDEST()+" NOMBRE_EST:"+e.getNOMBRE_EST()+" ID_SERV:"+e.getID_SERV()+"\n");
                if(e.getID_SERV().trim().equals("Alojamiento")){
                    Alojamientos.add(e);
                }
                if(e.getID_SERV().trim().equals("Restaurante")){
                    Restaurantes.add(e);
                }
                if(e.getID_SERV().trim().equals("Peaje")){
                    Peajes.add(e);
                }
                if(e.getID_SERV().trim().equals("EstServicio")){
                    EstServicio.add(e);
                }
                if(e.getID_SERV().trim().equals("Parqueadero")){
                    Parqueaderos.add(e);
                }
                if(e.getID_SERV().trim().equals("Taller")){
                    Talleres.add(e);
                }
            }
            if(Alojamientos.size()>0 && Restaurantes.size()>0 && Peajes.size()>0
                && EstServicio.size()>0 && Parqueaderos.size()>0 && Talleres.size()>0) {
                System.out.println("HAY VALORES EN LA LISTAS BASE - \n " +
                        "-Alojamientos\n " +
                        "-Restaurantes\n " +
                        "-Peajes\n " +
                        "-EstServicio\n " +
                        "-Parqueaderos\n " +
                        "-Talleres\n ");
            }
        }else{
            //notifyUser("NO HAY ESTABLECIMIENTOS DISPONIBLES");
            System.out.println("NO HAY ESTABLECIMIENTOS DISPONIBLES");
        }

    }

    private void notifyUser(String message){

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}


