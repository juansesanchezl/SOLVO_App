package com.example.solvo.solvo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Juancho-PC on 28/03/2018.
 */

public class GetNearByPlacesData extends AsyncTask<Object,String,String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    List<HashMap<String,String>> nearbyPlaceList = null;



    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){
        System.out.println("ENTRO A:"+s);
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String,String>> nearbyPlaceList){
        System.out.println("Cantidad:"+nearbyPlaceList.size());
        System.out.println("INFORMACION TOTAL:");
        for(int j=0; j<nearbyPlaceList.size(); ++j ){

            HashMap<String,String> hashMap = nearbyPlaceList.get(j);
            for (String name: hashMap.keySet()){

                String key =name.toString();
                String value = hashMap.get(name).toString();
                System.out.println("<"+key+">" + "-> " + value);


            }
        }

        for (int i = 0; i < nearbyPlaceList.size(); ++i) {
            /*
            NOMBRE:  place_name
            DIRECCIÓN: vicinity y/o formatted_address
            LATITUD: lat
            LONGITUD: lng
            NIVEL DE PRECIO: price_level (0: gratis,1: barato,2: moderado,3: caro,4: muy caro)
            CALIFICACIÓN: rating
            DISPONIBILIDAD: open_now
            */
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String,String> googlePlace =  nearbyPlaceList.get(i);


            int valoracionPrecio = 0;
            String nivel_precio = "No Disponible";
            String disponibilidad = "No Disponible";
            double calificacion = 0.0;
            String id_lugar = googlePlace.get("place_id");
            String nombre_estbl = googlePlace.get("place_name");
            String direccion = googlePlace.get("vicinity");
            if(!googlePlace.get("price_level").isEmpty()) {
                valoracionPrecio = Integer.parseInt(googlePlace.get("price_level"));
                switch (valoracionPrecio){
                    case 0:
                        nivel_precio = "Gratis";
                    break;
                    case 1:
                        nivel_precio = "Barato";
                    break;
                    case 2:
                        nivel_precio = "Moderado";
                    break;
                    case 3:
                        nivel_precio = "Costoso";
                    break;
                    case 4:
                        nivel_precio = "Muy Costoso";
                    break;

                }

            }
            if(!googlePlace.get("rating").isEmpty()) {
                calificacion = Double.parseDouble(googlePlace.get("rating"));
            }
            if((Boolean.parseBoolean(googlePlace.get("open_now")))==true){
                disponibilidad = "Abierto";
            }else if((Boolean.parseBoolean(googlePlace.get("open_now")))== false){
                disponibilidad = "Cerrado";
            }
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            System.out.println("-Nombre:"+nombre_estbl+"-Cercania:"+direccion+"-Latitud:"+lat+"-Longitud:"+lng);
            System.out.println("-ID:"+id_lugar+"-NivelPrecio:"+nivel_precio+"-Calificacion:"+calificacion+"-Disponibilidad"+disponibilidad);
            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title("-"+nombre_estbl+"-"+direccion);
            markerOptions.snippet("-"+nivel_precio+"-"+calificacion+"-"+disponibilidad);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            Context context = Restaurante.getContext();
            //GoogleMap.InfoWindowAdapter windowAdapter = new VentanaInfoMarcador( Restaurante.getContext(),nivel_precio,disponibilidad,calificacion,nombre_estbl,direccion);
            //mMap.setInfoWindowAdapter(windowAdapter);
            mMap.addMarker(markerOptions).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        }

    }

    public List<HashMap<String,String>> datosLugaresCercanos(){
        return nearbyPlaceList;

    }

}
