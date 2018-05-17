package com.example.solvo.solvo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RecyclerViewClases.LineAdapter;
import com.SQLib.Comentario;
import com.SQLib.ConsultasDB;
import com.solvo.awsandroid.AWSLoginModel;

import java.util.ArrayList;
import java.util.List;

public class FunComentar extends AppCompatActivity {

    List<Comentario> listaComentarEsta = new ArrayList<>();
    Context contextFunCom;
    private LineAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_comentar);
        String ID_EST = "";
        String Tipo_Est;
        String Nombre_Est;
        contextFunCom = getApplicationContext();
        ConsultasDB.obtenerComent(contextFunCom);

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
                inicializarFunComentario(ID_EST, Tipo_Est, Nombre_Est);

            }
        }

        Button btnCom = (Button) findViewById(R.id.btnComentarEst);
        final EditText tvCom = (EditText) findViewById(R.id.comentUs);
        final String idCom = ID_EST;
        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(tvCom.getText().toString().trim().equals(""))){
                    //ConsultasDB.obtenCantidadComent(contextMenu,"ESTE ES UN COMENTARIO COOLS","juansesanchezl","E04");
                    String Coment = tvCom.getText().toString().trim();
                    String user = AWSLoginModel.getSavedUserName(FunComentar.this);
                    ConsultasDB.obtenCantidadComent(contextFunCom,Coment,user.trim(),idCom);
                }else{
                    notifyUser("LA CAJA DE COMENTARIO ESTA EN BLANCO");
                }
            }
        });

    }

    private void inicializarFunComentario(String id_est, String tipo_est, String nombre_Est) {

        TextView tvNombreEst = findViewById(R.id.Nom_Establecimiento);
        tvNombreEst.setText(nombre_Est);

        if(tipo_est.equals("Restaurante")){
            if(MenuPrincipal.Restaurantes.size()>0) {
                llenarComentarios(id_est);

            }
        }else if(tipo_est.equals("Parqueadero")){
            if(MenuPrincipal.Parqueaderos.size()>0) {
                llenarComentarios(id_est);
            }

        }else if(tipo_est.equals("Alojamiento")){
            if(MenuPrincipal.Alojamientos.size()>0) {
                llenarComentarios(id_est);
            }

        }else if(tipo_est.equals("EstServicio")){
            if(MenuPrincipal.EstServicio.size()>0) {
                llenarComentarios(id_est);
            }

        }else if(tipo_est.equals("Peaje")){
            if(MenuPrincipal.Peajes.size()>0) {
                llenarComentarios(id_est);
            }

        }else if(tipo_est.equals("Taller")){
            if(MenuPrincipal.Talleres.size()>0) {
                llenarComentarios(id_est);
            }

        }


    }

    private void llenarComentarios(String id_est){
        for(Comentario com : MenuPrincipal.listaComentarios){
            if(com.getID_EST().equals(id_est)){
                //System.out.println("Comentario:"+com.getCOMENTARIO()+" **"+com.getCOND_USER());
                String coment = "["+com.getCOND_USER()+"]: "+com.getCOMENTARIO();
                System.out.println(coment);
                listaComentarEsta.add(com);
            }
        }
        if(listaComentarEsta.size()>0){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvListComent);
            //recyclerView.setLayoutManager(new LinearLayoutManager(this));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new LineAdapter(listaComentarEsta);
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


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
