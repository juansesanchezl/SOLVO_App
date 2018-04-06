package com.example.solvo.solvo;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by camilo on 4/04/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    private String nivel_precio;
    private String disponibilidad;
    private double calificacion;
    private String nombre_estbl;
    private String direccion;

    public CustomInfoWindowAdapter(Activity context, String nPrecio, String nDisp, Double nCal, String nNom, String nDir){
        this.context = context;
        this.nivel_precio = nPrecio;
        this.disponibilidad = nDisp;
        this.calificacion = nCal;
        this.nombre_estbl = nNom;
        this.direccion = nDir;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvSubTitle = (TextView) view.findViewById(R.id.tv_subtitle);

        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(marker.getSnippet());


        return view;
    }
}