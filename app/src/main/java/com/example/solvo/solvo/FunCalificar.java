package com.example.solvo.solvo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.SQLib.Comentario;
import com.SQLib.ConsultasDB;
import com.solvo.awsandroid.AWSLoginModel;

import java.util.ArrayList;
import java.util.List;

public class FunCalificar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_calificar);
        String ID_EST;
        String Tipo_Est;
        String Nombre_Est;
        List<Comentario> listaComentarEsta = new ArrayList<>();
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ID_EST = "";
                Tipo_Est = "";
                Nombre_Est = "";
            }else {
                ID_EST = extras.getString("id");
                Tipo_Est = extras.getString("tipo");
                Nombre_Est = extras.getString("name");
                inicializarFunCalif(ID_EST, Tipo_Est, Nombre_Est );

            }
        }
    }

    private void inicializarFunCalif(String id_est, String tipo_est, String nombre_Est) {
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
