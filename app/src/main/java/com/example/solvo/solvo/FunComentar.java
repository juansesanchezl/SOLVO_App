package com.example.solvo.solvo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RecyclerViewClases.comentAdapter;
import com.SQLib.Comentario;
import com.SQLib.ConsultasDB;
import com.solvo.awsandroid.AWSLoginModel;

import java.util.ArrayList;
import java.util.List;

public class FunComentar extends AppCompatActivity {

    Context contextFunCom;
    List<Comentario> listaComentarEsta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_comentar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        String ID_EST = "";
        String Tipo_Est = "";
        String Nombre_Est = "";
        contextFunCom = getApplicationContext();

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
                inicializarFunComentario(ID_EST, Nombre_Est);

            }
        }

        Button btnCom = (Button) findViewById(R.id.btnComentarEst);
        final EditText tvCom = (EditText) findViewById(R.id.comentUs);
        final String idCom = ID_EST;
        final String nombrest = Nombre_Est;
        final String tipo = Tipo_Est;
        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(tvCom.getText().toString().trim().equals(""))){
                    notifyUser("GRACIAS SU COMENTARIO SE ESTA AÑADIENDO...");
                    String Coment = tvCom.getText().toString().trim();
                    String user = AWSLoginModel.getSavedUserName(FunComentar.this);
                    ConsultasDB.obtenCantidadComent(contextFunCom,Coment,user.trim(),idCom);
                    int puntosantes = Integer.parseInt(MenuPrincipal.conductorActual.getPuntos());
                    int puntosdespues = puntosantes + 5;
                    String puntosTotal = "" + puntosdespues;
                    ConsultasDB.actualizarPuntosSolvo(contextFunCom, user.trim(),puntosTotal);
                    FunComentar.this.finish();
                }else{
                    notifyUser("LA CAJA DE COMENTARIO ESTA EN BLANCO");
                }
            }
        });

    }



    public void inicializarFunComentario(String id_est, String nombre_Est) {

        TextView tvNombreEst = findViewById(R.id.Nom_Establecimiento);
        tvNombreEst.setText(nombre_Est);

        if(MenuPrincipal.listaComentarios.size()>0){
            llenarComentarios(id_est);
        }

    }

    private void llenarComentarios(String id_est){
        listaComentarEsta.clear();
        for(Comentario com : MenuPrincipal.listaComentarios){
            if(com.getID_EST().equals(id_est)){
                String coment = "["+com.getCOND_USER()+"]: "+com.getCOMENTARIO();
                System.out.println(coment);
                listaComentarEsta.add(com);
            }
        }
        if(listaComentarEsta.size()>0){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvListComent);
            comentAdapter adapter = new comentAdapter(listaComentarEsta);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }else{
            notifyUser("ESTE ESTABLECIMIENTO NO TIENE COMENTARIOS AÚN");
        }
    }


    private void notifyUser(String message){

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        String who = AWSLoginModel.getSavedUserName(FunComentar.this);
        cambiarEstado(who,"ACTIVO");

    }
    @Override
    protected void onPause() {
        super.onPause();

        String who = AWSLoginModel.getSavedUserName(FunComentar.this);
        cambiarEstado(who,"INACTIVO");

    }
    public void cambiarEstado(String user, String estado){

        ConsultasDB.cambiarEstado(FunComentar.this,user,estado);
    }
}
