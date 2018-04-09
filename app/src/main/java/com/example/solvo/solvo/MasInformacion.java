package com.example.solvo.solvo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MasInformacion extends AppCompatActivity {

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
        double lat;
        double lng;
        String tipo;
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = null;
                name = null;
                dir = null;
                precio = null;
                calif = null;
                disp = null;
                lat = 0;
                lng = 0;
                tipo = null;
            }else {
                tipo = extras.getString("tipo");
                id = extras.getString("id");
                name = extras.getString("name");
                dir = extras.getString("dir");
                precio = extras.getString("precio");
                calif = extras.getString("calif");
                disp  = extras.getString("disp");
                lat  = extras.getDouble("lat");
                lng  = extras.getDouble("lng");

                TextView tvTipo = (TextView) findViewById(R.id.tipoInfo);
                TextView tvId = (TextView) findViewById(R.id.id_mrk);
                TextView tvName = (TextView) findViewById(R.id.nom_mrk);
                TextView tvDir = (TextView) findViewById(R.id.dir_mrk);
                TextView tvPrecio = (TextView) findViewById(R.id.precio_mrk);
                TextView tvCalif = (TextView) findViewById(R.id.calf_mrk);
                TextView tvDisp = (TextView) findViewById(R.id.disp_mrk);

                tvTipo.setText(tipo);
                tvId.setText(id);
                tvName.setText(name);
                tvDir.setText(dir);
                tvPrecio.setText(precio);
                tvCalif.setText(calif);
                tvDisp.setText(disp);

            }
        }


    }
}
