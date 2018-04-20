package com.example.solvo.solvo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class rutausuarioestb extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;
    Double latti, lnggi, lattf, lnggf;
    String nombreEstablecimiento = null;
    int iterador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutausuarioestb);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "BOTÓN COOL", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listPoints = new ArrayList<>();
        listPoints.clear();
        double lati, latf;
        double lngi, lngf;
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                lati = 0;
                latf = 0;
                lngi = 0;
                lngf = 0;
            }else{
                lati = extras.getDouble("lati");
                lngi = extras.getDouble("lngi");
                latf  = extras.getDouble("latf");
                lngf  = extras.getDouble("lngf");
                nombreEstablecimiento = extras.getString("establ");
                latti = lati;
                lnggi = lngi;
                lattf = latf;
                lnggf = lngf;

                LatLng latLngi = new LatLng(lati,lngi);
                LatLng latLngf = new LatLng(latf,lngf);
                System.out.println("POSICION----");
                System.out.println("Lati:"+lati+" lngi:"+lngi);
                System.out.println("Latf:"+latf+" lngf:"+lngf);
                listPoints.add(latLngi);
                listPoints.add(latLngf);
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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
        String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
        taskRequestDirections.execute(url);
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

    private String getRequestUrl(LatLng origin, LatLng dest) {

        //Value of origin
        String str_org = "origin=" + origin.latitude +","+origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude+","+dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_org +"&" + str_dest + "&" +sensor+"&" +mode;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
        System.out.println("GETREQUESTURL->"+url);
        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";

        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Parse json here
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>> > {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;

            PolylineOptions polylineOptions = null;


            for (List<HashMap<String, String>> path : lists) {

                points = new ArrayList();
                polylineOptions = new PolylineOptions();


                for (HashMap<String, String> point : path) {

                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat,lon));
                }

                System.out.println("Point:"+points.toString());
                polylineOptions.addAll(points);
                polylineOptions.width(10);
                polylineOptions.color(Color.rgb(255,165,0));
                //polylineOptions.color(Color.RED);
                polylineOptions.geodesic(true);
            }

            if (polylineOptions!=null) {
                mMap.addPolyline(polylineOptions);
                LatLng latLngi = new LatLng(latti,lnggi);
                LatLng latLngf = new LatLng(lattf,lnggf);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLngf);
                System.out.println("EST:"+nombreEstablecimiento);
                if(nombreEstablecimiento == null) {
                    markerOptions.title("DESTINO");
                }else{
                    markerOptions.title(nombreEstablecimiento);
                }
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngi));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(1.0f));
            } else {
                Toast.makeText(getApplicationContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }
    }






}
