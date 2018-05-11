package com.example.solvo.solvo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.SQLib.ConsultasDB;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.solvo.awsandroid.AWSLoginModel;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Taller extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener {

    //Objetos
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static Context contexta;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 1000000;
    double latitudeUser, longitudeUser;
    public List<HashMap<String,String>> talleres = null;
    String tipoServ = "Ninguno";
    int iterador = 0;
    String API_PLACES_KEY = "AIzaSyCdzg_lWvwmqIAFkB2mNL-yqyIsJ99o8GI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taller);
        contexta = getApplicationContext();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, Html.fromHtml("<font color=\"#FFBF00\">CARGANDO TALLERES...</font>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                visualizarTalleres();
            }
        });
    }

    public static Context getContext(){
        return contexta;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Cuando se brinda el permiso requerido
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(googleApiClient == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }else {
                        // Cuando no se brinda el permiso requerido
                        Toast.makeText(this,"Permiso no concedido !!",Toast.LENGTH_LONG).show();
                    }
                    return;
                }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(iterador == 0) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
                    mMap.moveCamera(center);
                    mMap.animateCamera(zoom);
                    iterador++;
                }
            }
        });
    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void visualizarTalleres(){
        Object dataTransfer[] = new Object[3];
        String url = "";
        GetNearByPlacesData getNearByPlacesData;
        String googlePDRestaurante;
        System.out.println("TALLERES");
        //mMap.clear();
        String parking = "car_repair";
        url = getUrl(latitudeUser, longitudeUser, parking);

        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        getNearByPlacesData = new GetNearByPlacesData();
        try {
            googlePDRestaurante = getNearByPlacesData.execute(dataTransfer).get();
            DataParser parser = new DataParser();
            talleres = parser.parse(googlePDRestaurante);
            guardarLugares(talleres);
            tipoServ = "TALLERES";

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(talleres == null){
            System.out.println("Esta Vacio talleres");
            talleres = getNearByPlacesData.nearbyPlaceList;
        }
        Toast.makeText(Taller.this, "Mostrando Talleres Cercanos", Toast.LENGTH_LONG).show();
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace){

        if(latitude == 0.0 && longitude == 0.0){
            checkLocationPermission();
        }
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&types="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+API_PLACES_KEY);
        System.out.println("URL->"+googlePlaceUrl.toString());
        return googlePlaceUrl.toString();

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        //Mover la camara al haber un cambio de localización y hacerle un zoom x10
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));
        latitudeUser = location.getLatitude();
        longitudeUser = location.getLongitude();

        if(googleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION ) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            return false;
        }else{
            return true;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(Taller.this, "La conexión esta suspendida", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(Taller.this, "La conexión tiene fallos, por favor verifique", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        System.out.println("ENTRO AL MARCADOR CON ID:"+marker.getId());
        Intent i = new Intent(Taller.this,MasInformacion.class);
        if(tipoServ.equals("TALLERES")){
            System.out.println("TAMAÑO:"+ talleres.size());
            for (int j = 0; j < talleres.size(); ++j) {
                HashMap<String,String> googlePlace =  talleres.get(j);
                String id_lugar = googlePlace.get("place_id");
                System.out.println(id_lugar+"--|--"+marker.getSnippet());
                if(id_lugar.equals(marker.getSnippet())){
                    String nombre_estbl = googlePlace.get("place_name");
                    String direccion = googlePlace.get("vicinity");
                    String nivel_precio = "No Disponible";
                    int valoracionPrecio;
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
                    String calificacion = googlePlace.get("rating");
                    String disponibilidad = "No Disponible";
                    double lat = Double.parseDouble(googlePlace.get("lat"));
                    double lng = Double.parseDouble(googlePlace.get("lng"));
                    //String icon = googlePlace.get("icon");
                    //String icon = "https://upload.wikimedia.org/wikipedia/commons/d/d1/6.4_%28Road_sign%29.png";
                    String icon = "http://pegasus.javeriana.edu.co/~CIS1730CP08/img/ICONOS/Taller.png";
                    if((Boolean.parseBoolean(googlePlace.get("open_now")))==true){
                        disponibilidad = "Abierto";
                    }else if((Boolean.parseBoolean(googlePlace.get("open_now")))== false){
                        disponibilidad = "Cerrado";
                    }
                    i.putExtra("id", id_lugar);
                    i.putExtra("name", nombre_estbl);
                    i.putExtra("dir", direccion);
                    i.putExtra("precio", nivel_precio);
                    i.putExtra("calif", calificacion);
                    i.putExtra("disp", disponibilidad);
                    i.putExtra("lati",latitudeUser);
                    i.putExtra("lngi",longitudeUser);
                    i.putExtra("latf", lat);
                    i.putExtra("lngf", lng);
                    i.putExtra("tipo", tipoServ);
                    i.putExtra("icono",icon);
                    System.out.println("ID:"+id_lugar+" NAME:"+nombre_estbl);
                    startActivity(i);
                }
            }
        }
        return false;
    }

    private void guardarLugares(List<HashMap<String,String>> nearbyPlaceList){
        System.out.println("Cantidad--LOCAL->"+nearbyPlaceList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        String who = AWSLoginModel.getSavedUserName(Taller.this);
        cambiarEstado(who,"ACTIVO");

    }
    @Override
    protected void onPause() {
        super.onPause();

        String who = AWSLoginModel.getSavedUserName(Taller.this);
        cambiarEstado(who,"INACTIVO");

    }
    public void cambiarEstado(String user, String estado){

        ConsultasDB.cambiarEstado(Taller.this,user,estado);
    }


}
