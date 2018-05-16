package com.SQLib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.correoE.EnviarRestContra;
import com.correoE.enviarCorreo;
import com.example.solvo.solvo.MenuPrincipal;
import com.example.solvo.solvo.RestablecerContra;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class ConsultasDB {

    public static final String REGISTRAR_URL = "http://54.145.165.9/php-solvo/registrar.php";
    public static final String CAMBIARESTADO_URL = "http://54.145.165.9/php-solvo/cambiarestado.php";
    public static final String OBTENERCONTRA_URL = "http://54.145.165.9/php-solvo/obtenercontra.php";
    public static final String OBTENERESTBL_URL = "http://54.145.165.9/php-solvo/obtenerEstabl.php";

    static Context elContexto;
    public static String obtenercon = "";
    public static boolean obtener = false;


    public static boolean checkNetworkConnection(Context context){
        elContexto = context;
        /*ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo.isConnected()){
            System.out.println("SE CONTECTO");
        }else{
            notifyUser("NO HAY CONEXION, POR FAVOR VERIFICA");
        }
        return(networkInfo!=null && networkInfo.isConnected());
        */
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo!=null && networkInfo.isConnected()) {
                //notifyUser("NO HAY CONEXION, POR FAVOR VERIFICA");
                return true;
            }else{
                notifyUser("NO HAY CONEXION, POR FAVOR VERIFICA");
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    public static void obtenerEstabl(Context context, final DatabaseHelper db) {
        elContexto = context;
        if (checkNetworkConnection(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, OBTENERESTBL_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        System.out.println("TAMAÑO--->"+jsonarray.length());
                        //List<Establecimiento> estableList = new ArrayList<>();
                        for(int i=0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String idest = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("IDEST")),"UTF-8").trim();
                            String nombre_est = java.net.URLDecoder.decode(quitarPorcentajes(jsonobject.getString("NOMBRE_EST")),"UTF-8").trim();
                            String id_serv = java.net.URLDecoder.decode(quitarPorcentajes(jsonobject.getString("ID_SERV")),"UTF-8").trim();
                            String dir_est = java.net.URLDecoder.decode(quitarPorcentajes(jsonobject.getString("DIR_EST")),"UTF-8").trim();
                            String telefono_est = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("TELEFONO_EST")),"UTF-8").trim();
                            String email_est = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("EMAIL_EST")),"UTF-8").trim();
                            double lat_est = Double.parseDouble(jsonobject.getString("LAT_EST").trim());
                            double long_est =  Double.parseDouble(jsonobject.getString("LONG_EST").trim());
                            String niv_precio = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("NIV_PRECIO")),"UTF-8").trim();
                            float calificacion = Float.parseFloat(jsonobject.getString("CALIFICACION").trim());

                           // Establecimiento e = new Establecimiento(idest,nombre_est,id_serv,dir_est,telefono_est,email_est,lat_est,long_est,niv_precio,calificacion);
                            //estableList.add(e);



                            System.out.println("["+(i+1)+"]------- "+idest+","+nombre_est+","+id_serv+","+dir_est
                                    +","+telefono_est+","+email_est+","+lat_est+","+long_est+","+niv_precio+","+calificacion);
                            System.out.println("<----------------------------->");
                            db.insertEstablecimiento(idest,nombre_est,id_serv,dir_est,telefono_est,email_est,lat_est,long_est,niv_precio,calificacion);


                        }
                        MenuPrincipal.estableList.addAll(db.getEstablecimientos());
                        //List<Establecimiento> establecimientos = db.getEstablecimientos();
                        notifyUser("LLEGARON-"+ MenuPrincipal.estableList.size()+"-ESTBL");
                        MenuPrincipal.listaEstaLlena = true;
                        MenuPrincipal.imprimirLista(MenuPrincipal.estableList);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

            }) {
                /*@Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("ENTRO*****3");
                    Map<String, String> params = new HashMap<>();
                    params.put("USUARIO", usuarioCond);
                    //return super.getParams();
                    return params;
                }*/
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");


        }
    }

    public static void obtenerContra(Context context, final String usuarioCond){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, OBTENERCONTRA_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        for(int i=0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String contrasenia = jsonobject.getString("CONTRASENIA");
                            if(!contrasenia.equals("")){
                                if(contrasenia.equals("No Disponible")){
                                    obtenercon = "No Disponible";
                                    notifyUser("ESTE USUARIO NO EXISTE -- REGISTRATE!");

                                }else{
                                    obtenercon = contrasenia;
                                    Object dataTransfer[] = new Object[3];
                                    dataTransfer[0] = usuarioCond;
                                    dataTransfer[1] = contrasenia;
                                    EnviarRestContra enviarRestContra = new EnviarRestContra();
                                    enviarRestContra.execute(dataTransfer);
                                    notifyUser("Revisa tu correo!!!!!");
                                }
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("ENTRO*****3");
                    Map<String,String> params = new HashMap<>();
                    params.put("USUARIO",usuarioCond);
                    //return super.getParams();
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");

        }

    }

    public static void cambiarEstado(Context context, final String userU, final String estado){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CAMBIARESTADO_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("response");
                        if(Response != null){
                            System.out.println(Response);
                        }
                        if(Response.equals("Estado Cambiado")){
                            System.out.println("ENTRO*****2-4");
                            System.out.println("ESTADO DE CONDUCTOR CAMBIADO EN BD");
                            //notifyUser("ESTADO DE CONDUCTOR CAMBIADO EN BD");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("ENTRO*****3");
                    Map<String,String> params = new HashMap<>();
                    params.put("USUARIO",userU);
                    params.put("ESTADO",estado);

                    //return super.getParams();
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");


        }

    }




    public void enviarCorreo(){

    }

    public static void insertarConductorBD(Context context, final String userU, final String emailU, final String passwordU, final String phoneU, final String nameU, final String fechaNacU, final String ciudadUs, final String genero){

        elContexto = context;
        System.out.println("ENTRO**INSERTAR");
        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRAR_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println("Respuesta:-->"+response);
                                System.out.println("ENTRO*****2");
                                JSONObject jsonObject = new JSONObject(response);
                                System.out.println("ENTRO*****2-1");
                                String Response = jsonObject.getString("response");
                                System.out.println("ENTRO*****2-3");
                                if(Response != null){
                                    System.out.println(Response);
                                }
                                if(Response.equals("Conductor Insertado")){
                                    System.out.println("ENTRO*****2-4");
                                    notifyUser("CONDUCTOR INSERTADO EN BD");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("ENTRO*****3");
                    Map<String,String> params = new HashMap<>();
                    //java.net.URLDecoder.decode(;
                    System.out.println("-->"+normalizarString(nameU)+"--"+normalizarString(ciudadUs));
                    params.put("USUARIO",userU);
                    params.put("NOMBRECOMPL",normalizarString(nameU));
                    params.put("NUMCONTACTO",phoneU);
                    params.put("FECHANAC",fechaNacU);
                    params.put("GENERO",genero);
                    params.put("CORREO",emailU);
                    params.put("PASSWORD",passwordU);
                    params.put("CIUDAD",normalizarString(ciudadUs));

                    //return super.getParams();
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");
        }else{
            notifyUser("NO HAY CONEXIÓN DE INTERNET");
        }
    }


    public static String normalizarString(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public static String quitarPorcentajes(String cadena){
        cadena = cadena.replaceAll("%20", " ");
        cadena = cadena.replaceAll("%28","(");
        cadena = cadena.replaceAll("%29",")");
        cadena = cadena.replaceAll("%3B",";");
        cadena = cadena.replaceAll("%40","@");
        cadena = cadena.replaceAll("%23","#");
        cadena = cadena.replaceAll("%2F","/");
        cadena = cadena.replaceAll("%3F","?");
        cadena = cadena.replaceAll("%3A",":");
        cadena = cadena.replaceAll("%25","%");

        cadena = cadena.replaceAll("%96","-");
        cadena = cadena.replaceAll("%ED","í");
        cadena = cadena.replaceAll("%F1","ñ");
        cadena = cadena.replaceAll("%92","’");
        return cadena;
    }

    private static void notifyUser(String message){
        Toast.makeText(elContexto, message, Toast.LENGTH_SHORT).show();
    }
}