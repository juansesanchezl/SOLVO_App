package com.example.solvo.solvo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.SQLib.Comentario;
import com.SQLib.ConsultasDB;
import com.solvo.awsandroid.AWSLoginModel;

import java.util.ArrayList;
import java.util.List;

public class FunCalificar extends AppCompatActivity {

    public RatingBar ratingBar;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_calificar);
        context = getApplicationContext();
        String ID_EST;
        String Tipo_Est;
        String Nombre_Est;
        float Calificacion;
        float califEST = 0;
        String idest = "";
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ID_EST = "";
                Tipo_Est = "";
                Nombre_Est = "";
                Calificacion = 0;
            }else {
                ID_EST = extras.getString("id");
                Tipo_Est = extras.getString("tipo");
                Nombre_Est = extras.getString("name");
                Calificacion = extras.getFloat("calif");
                inicializarFunCalif(ID_EST, Tipo_Est, Nombre_Est, Calificacion );
                califEST = Calificacion;
                idest = ID_EST;
            }
        }

        Button btnCalificar = (Button) findViewById(R.id.btnCalificar);
        final float finalCalifEST = califEST;
        final String finalidest = idest;
        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float califUsuario = ratingBar.getRating();
                //finalCalifEST
                TextView tvCalif = (TextView) findViewById(R.id.CalifNum);
                String califUser = "" + califUsuario;
                tvCalif.setText(califUser);
                //notifyUser("Calificación: "+califUser);
                String usuarioActual = AWSLoginModel.getSavedUserName(FunCalificar.this);
                if(!finalidest.isEmpty()) {
                    ConsultasDB.obtenerCantidadCalif(context, califUser.trim(), usuarioActual.trim(), finalidest);
                    int puntosantes = Integer.parseInt(MenuPrincipal.conductorActual.getPuntos());
                    int puntosdespues = puntosantes + 5;
                    String puntosTotal = "" + puntosdespues;
                    ConsultasDB.actualizarPuntosSolvo(context, usuarioActual.trim(),puntosTotal);
                    notifyUser("GRACIAS, POR SU CALIFICACIÓN DE "+califUser+", SE ESTA AÑADIENDO...");
                    FunCalificar.this.finish();
                }
            }
        });
    }

    private void inicializarFunCalif(String id_est, String tipo_est, String nombre_Est, float calificacion) {

        ratingBar = (RatingBar) findViewById(R.id.rBCalifEst);
        ratingBar.setRating(calificacion);
        TextView tvCalif = (TextView) findViewById(R.id.CalifNum);
        String califUser = "" + calificacion;
        tvCalif.setText(califUser);



    }
    private void notifyUser(String message){

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        String who = AWSLoginModel.getSavedUserName(FunCalificar.this);
        cambiarEstado(who,"ACTIVO");

    }
    @Override
    protected void onPause() {
        super.onPause();

        String who = AWSLoginModel.getSavedUserName(FunCalificar.this);
        cambiarEstado(who,"INACTIVO");

    }
    public void cambiarEstado(String user, String estado){

        ConsultasDB.cambiarEstado(FunCalificar.this,user,estado);
    }
}
