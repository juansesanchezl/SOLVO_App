package com.example.solvo.solvo;

import android.Manifest;

import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.location.LocationListener;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class Restaurante extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //Objetos
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    String API_PLACES_KEY = "AIzaSyCdzg_lWvwmqIAFkB2mNL-yqyIsJ99o8GI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Cuando se brinda el permiso requerido
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        // Add a marker in Sydney and move the camera
        /*LatLng bogota = new LatLng(4.624335, -74.063644);
        addMarkerMap(4.630474, -74.066813, "SUBWAY");
        addMarkerMap(4.633597, -74.068273, "POPEYE");
        addMarkerMap(4.631736, -74.064067, "DOMINO'S PIZZA");
        addMarkerMap(4.624335, -74.063644, "BOGOTÁ");*/
    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    public void addMarkerMap (double Lat, double Log, String Titulo){
        LatLng latLng = new LatLng(Lat,Log);
        mMap.addMarker(new MarkerOptions().position(latLng).title(Titulo));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        /*mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_black_24dp))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(latLng.latitude, latLng.longitude)));*/
    }

    public void permisosLocalizacion() {
        if (ContextCompat.checkSelfPermission(Restaurante.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Restaurante.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                mMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(Restaurante.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    public void onClick(View view){

        Object dataTransfer[] = new Object[2];
        String url = "";
        GetNearByPlacesData getNearByPlacesData;

        if(view.getId() == R.id.btnBusq) {
            EditText locDestino = this.findViewById(R.id.TextBusqu);
            String location = locDestino.getText().toString();
            List<Address> addressList = null;
            MarkerOptions mo = new MarkerOptions();
            if (!location.equals("")) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < addressList.size(); ++i) {
                    Address miDireccion = addressList.get(i);
                    LatLng latLng = new LatLng(miDireccion.getLatitude(), miDireccion.getLongitude());
                    mo.position(latLng);
                    mo.title("Tu búsqueda");
                    mMap.addMarker(mo);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }

            }
        }else if(view.getId() == R.id.btnRest) {


            System.out.println("RESTAURANTES");
            mMap.clear();
            String restaurante = "restaurant";
            url = getUrl(latitude, longitude, restaurante);

            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            getNearByPlacesData = new GetNearByPlacesData();
            getNearByPlacesData.execute(dataTransfer);

            Toast.makeText(Restaurante.this, "Mostrando Restaurantes Cercanos", Toast.LENGTH_LONG).show();

        }else if(view.getId() == R.id.btnPark) {

            System.out.println("PARQUEADEROS");
            mMap.clear();
            String parking = "parking";
            url = getUrl(latitude, longitude, parking);

            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            getNearByPlacesData = new GetNearByPlacesData();
            getNearByPlacesData.execute(dataTransfer);
            Toast.makeText(Restaurante.this, "Mostrando Parqueaderos Cercanos", Toast.LENGTH_LONG).show();


        }else if(view.getId() == R.id.btnEstSer) {

            System.out.println("ESTACIONES-DE-SERVICIO");
            mMap.clear();
            String EstServicio = "gas_station";
            url = getUrl(latitude, longitude, EstServicio);

            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            getNearByPlacesData = new GetNearByPlacesData();
            getNearByPlacesData.execute(dataTransfer);
            Toast.makeText(Restaurante.this, "Mostrando Estaciones de Servicio Cercanos", Toast.LENGTH_LONG).show();
        }


        /*if(view.getId() == R.id.btnBusq){
            EditText locDestino = this.findViewById(R.id.TextBusqu);
            String location = locDestino.getText().toString();
            List<Address> addressList = null;
            MarkerOptions mo = new MarkerOptions();
            if(! location.equals("")){
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location,5);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i=0; i<addressList.size();++i){
                    Address miDireccion = addressList.get(i);
                    LatLng latLng = new LatLng(miDireccion.getLatitude(),miDireccion.getLongitude());
                    mo.position(latLng);
                    mo.title("Tu búsqueda");
                    mMap.addMarker(mo);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }

            }
        }*/
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
        //googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+API_PLACES_KEY);
        System.out.println("URL->"+googlePlaceUrl.toString());
        /*StringBuilder googlePlaceUrl2 = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant");
        googlePlaceUrl2.append("&key="+API_PLACES_KEY);
        System.out.println("URL2->"+googlePlaceUrl2.toString());*/
        return googlePlaceUrl.toString();
        //return googlePlaceUrl2.toString();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("USTED ESTA AQUÍ");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        currentLocationMarker = mMap.addMarker(markerOptions);
        //Mover la camara al haber un cambio de localización y hacerle un zoom x10
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
        latitude = location.getLatitude();
        longitude = location.getLongitude();

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

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
