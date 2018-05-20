package com.example.solvo.solvo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.SQLib.ConsultasDB;
import com.solvo.awsandroid.AWSLoginModel;

import java.io.InputStream;
import java.net.URL;

public class MasInformacion extends AppCompatActivity {

    String nombreEstbl = null;
    Context contextmasinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_informacion);
        contextmasinfo = getApplicationContext();
        String id;
        String name;
        String dir;
        String precio;
        final float calif;
        String disp;
        final double lati, latf;
        final double lngi, lngf;
        String tipo;
        String icono;
        String tel;
        String email;

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = null;
                name = null;
                dir = null;
                precio = null;
                calif = 0;
                disp = null;
                lati = 0;
                latf = 0;
                lngi = 0;
                lngf = 0;
                tipo = null;
                icono = null;
                tel = null;
                email = null;
            }else {
                //tipo = extras.getString("tipo");
                tipo = extras.getString("tipo");
                System.out.println("TIPO-->"+tipo);
                id = extras.getString("id");
                name = extras.getString("name");
                dir = extras.getString("dir");
                precio = extras.getString("precio");
                calif = extras.getFloat("calif");
                //disp  = extras.getString("disp");
                lati = extras.getDouble("lati");
                lngi = extras.getDouble("lngi");
                latf  = extras.getDouble("latf");
                lngf  = extras.getDouble("lngf");
                icono = extras.getString("icono");
                tel = extras.getString("tel");
                email = extras.getString("email");
                nombreEstbl = name;

                TextView tvTipo = (TextView) findViewById(R.id.tipoInf);
                TextView tvId = (TextView) findViewById(R.id.id_mrk);
                TextView tvName = (TextView) findViewById(R.id.nom_mrk);
                TextView tvDir = (TextView) findViewById(R.id.dir_mrk);
                TextView tvTel = (TextView) findViewById(R.id.tel_mrk);
                TextView tvEmail = (TextView) findViewById(R.id.email_mrk);
                TextView tvPrecio = (TextView) findViewById(R.id.precio_mrk);
                TextView tvCalif = (TextView) findViewById(R.id.calf_mrk);
                //TextView tvDisp = (TextView) findViewById(R.id.disp_mrk);
                ImageView tvIcono = (ImageView) findViewById(R.id.image_mrk);
                RatingBar tvRating = (RatingBar) findViewById(R.id.ratingBar);
                Button tvRuta = (Button) findViewById(R.id.btnRuta);
                tvRuta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        trazarRuta(lati,lngi,latf,lngf);
                    }
                });
                System.out.println("Icono-->"+icono);
                System.out.println("Lati:"+lati+" lngi:"+lngi);
                System.out.println("Latf:"+latf+" lngf:"+lngf);
                Button tvNavegar = (Button) findViewById(R.id.btnNavegar);
                tvNavegar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navegarRuta(latf,lngf);
                    }
                });
                final String idesta = id;
                final String tipoesta = tipo;
                final String nameesta = name;
                final float calificacion = calif;

                        Button tvComentar = (Button) findViewById(R.id.btnComentarEst);
                tvComentar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MasInformacion.this, "ESTA FUNCIÓN SE ENCUENTRA EN CONSTRUCCIÓN", Toast.LENGTH_LONG).show();
                        //MasInformacion.this.startActivity(new Intent(MasInformacion.this, FunComentar.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        ConsultasDB.obtenerComent(contextmasinfo);
                        Intent funcom = new Intent(MasInformacion.this,FunComentar.class);
                        funcom.putExtra("id",idesta);
                        funcom.putExtra("tipo",tipoesta);
                        funcom.putExtra("name",nameesta);
                        startActivity(funcom);

                    }
                });

                Button tvCalificar = (Button) findViewById(R.id.btnCalificar);
                tvCalificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //MasInformacion.this.startActivity(new Intent(MasInformacion.this, FunCalificar.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        Intent funcalf = new Intent(MasInformacion.this,FunCalificar.class);
                        funcalf.putExtra("id",idesta);
                        funcalf.putExtra("tipo",tipoesta);
                        funcalf.putExtra("name",nameesta);
                        funcalf.putExtra("calif",calif);
                        startActivity(funcalf);
                    }
                });

                tvTipo.setText(tipo);
                tvId.setText(id);
                tvName.setText("Nombre: "+name);
                tvDir.setText("Dirección: "+dir);
                tvPrecio.setText("Nivel de Precio: "+precio);
                //tvDisp.setText("Disponibilidad: "+disp);
                tvTel.setText("Teléfono: "+tel);
                tvEmail.setText("Contacto: "+email);

                if(!(calif==0)) {
                    tvCalif.setText("Calificación: "+calif);
                    //tvRating.setRating(Float.parseFloat(calif));
                    tvRating.setRating(calif);
                }else{
                    tvCalif.setText("Calificación: No Disponible");
                }
                /*if(!icono.isEmpty()){
                    AsyncTask<String, Void, Bitmap> downLoadImageTask = new DownLoadImageTask(tvIcono).execute(icono);
                }*/
                //int id = getResources().getIdentifier("yourpackagename:drawable/" + , null, null);
                if(tipo.equals("Restaurante")) {
                    tvIcono.setImageDrawable(getResources().getDrawable(R.drawable.restaurante));
                }
                if(tipo.equals("Parqueadero")) {
                    tvIcono.setImageDrawable(getResources().getDrawable(R.drawable.parqueadero));
                }
                if(tipo.equals("Alojamiento")) {
                    tvIcono.setImageDrawable(getResources().getDrawable(R.drawable.alojamiento));
                }
                if(tipo.equals("EstServicio")) {
                    tvIcono.setImageDrawable(getResources().getDrawable(R.drawable.estacionservicio));
                }
                if(tipo.equals("Peaje")) {
                    tvIcono.setImageDrawable(getResources().getDrawable(R.drawable.peaje));
                }
                if(tipo.equals("Taller")) {
                    tvIcono.setImageDrawable(getResources().getDrawable(R.drawable.taller));
                }

            }
        }


    }



    private void trazarRuta(Double lati, Double lngi, Double latf, Double lngf){
        Intent i = new Intent(MasInformacion.this, rutausuarioestb.class);
        i.putExtra("lati",lati);
        i.putExtra("lngi",lngi);
        i.putExtra("latf", latf);
        i.putExtra("lngf", lngf);
        i.putExtra("establ",nombreEstbl);
        startActivity(i);

    }

    private void navegarRuta (Double latf, Double lngf){
        String latlng = latf+","+lngf;
        String who = AWSLoginModel.getSavedUserName(MasInformacion.this);
        cambiarEstado(who,"INACTIVO");
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latlng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        String who = AWSLoginModel.getSavedUserName(MasInformacion.this);
        cambiarEstado(who,"ACTIVO");
        ConsultasDB.obtenerComent(contextmasinfo);

    }
    @Override
    protected void onPause() {
        super.onPause();

        String who = AWSLoginModel.getSavedUserName(MasInformacion.this);
        cambiarEstado(who,"INACTIVO");

    }
    public void cambiarEstado(String user, String estado){

        ConsultasDB.cambiarEstado(MasInformacion.this,user,estado);
    }
}
