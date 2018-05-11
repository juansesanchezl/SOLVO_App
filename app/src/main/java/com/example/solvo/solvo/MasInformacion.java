package com.example.solvo.solvo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.Rating;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.SQLib.ConsultasDB;
import com.dynamodb.ManagerClass;
import com.solvo.awsandroid.AWSLoginModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.amazonaws.util.IOUtils.copy;

public class MasInformacion extends AppCompatActivity {

    String nombreEstbl = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_informacion);
        String id;
        String name;
        String dir;
        String precio;
        String calif;
        String disp;
        final double lati, latf;
        final double lngi, lngf;
        String tipo;
        String icono;

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = null;
                name = null;
                dir = null;
                precio = null;
                calif = null;
                disp = null;
                lati = 0;
                latf = 0;
                lngi = 0;
                lngf = 0;
                tipo = null;
                icono = null;
            }else {
                tipo = extras.getString("tipo");
                id = extras.getString("id");
                name = extras.getString("name");
                dir = extras.getString("dir");
                precio = extras.getString("precio");
                calif = extras.getString("calif");
                disp  = extras.getString("disp");
                lati = extras.getDouble("lati");
                lngi = extras.getDouble("lngi");
                latf  = extras.getDouble("latf");
                lngf  = extras.getDouble("lngf");
                icono = extras.getString("icono");
                nombreEstbl = name;

                TextView tvTipo = (TextView) findViewById(R.id.tipoInfo);
                TextView tvId = (TextView) findViewById(R.id.id_mrk);
                TextView tvName = (TextView) findViewById(R.id.nom_mrk);
                TextView tvDir = (TextView) findViewById(R.id.dir_mrk);
                TextView tvPrecio = (TextView) findViewById(R.id.precio_mrk);
                TextView tvCalif = (TextView) findViewById(R.id.calf_mrk);
                TextView tvDisp = (TextView) findViewById(R.id.disp_mrk);
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

                Button tvComentar = (Button) findViewById(R.id.btnComentar);
                tvComentar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MasInformacion.this, "ESTA FUNCIÓN SE ENCUENTRA EN CONSTRUCCIÓN", Toast.LENGTH_LONG).show();
                    }
                });
                tvTipo.setText(tipo);
                tvId.setText(id);
                tvName.setText("Nombre: "+name);
                tvDir.setText("Dirección: "+dir);
                tvPrecio.setText("Nivel de Precio: "+precio);
                tvDisp.setText("Disponibilidad: "+disp);

                if(!calif.isEmpty()) {
                    tvCalif.setText("Calificación: "+calif);
                    tvRating.setRating(Float.parseFloat(calif));
                }else{
                    tvCalif.setText("Calificación: No Disponible");
                }
                if(!icono.isEmpty()){
                    AsyncTask<String, Void, Bitmap> downLoadImageTask = new DownLoadImageTask(tvIcono).execute(icono);
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
