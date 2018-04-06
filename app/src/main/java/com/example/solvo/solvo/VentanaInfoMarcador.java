package com.example.solvo.solvo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class VentanaInfoMarcador implements GoogleMap.InfoWindowAdapter{


    private final View mWindow;
    private Context mcontext;
    private String nivel_precio;
    private String disponibilidad;
    private double calificacion;
    private String nombre_estbl;
    private String direccion;

    public VentanaInfoMarcador(Context context, String nPrecio, String nDisp, Double nCal, String nNom, String nDir){
        this.mcontext = context;
        this.mWindow = LayoutInflater.from(context).inflate(R.layout.activity_ventana_info_marcador,null);

        this.nivel_precio = nPrecio;
        this.disponibilidad = nDisp;
        this.calificacion = nCal;
        this.nombre_estbl = nNom;
        this.direccion = nDir;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        LlenarDatos( marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LlenarDatos( marker, mWindow);
        return mWindow;
    }

    private void LlenarDatos(Marker marker, View view){

        TextView mNombre = (TextView) view.findViewById(R.id.mNombre);
        TextView mDireccion = (TextView) view.findViewById(R.id.mDireccion);
        TextView mPrecio = (TextView) view.findViewById(R.id.mPrecio);
        TextView mCalificacion = (TextView) view.findViewById(R.id.mCalificacion);
        TextView mDisponibilidad = (TextView) view.findViewById(R.id.mDisponibilidad);

        mNombre.setText("Lugar:"+nombre_estbl);
        mDireccion.setText("Dirección:"+direccion);
        mPrecio.setText("Precio:"+nivel_precio);
        mCalificacion.setText("Calificación:"+Double.toString(calificacion));
        mDisponibilidad.setText("Disponibilidad:"+disponibilidad);

    }

}
