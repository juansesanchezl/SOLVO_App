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
import com.example.solvo.solvo.RestablecerContra;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.HashMap;
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
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return(networkInfo!=null && networkInfo.isConnected());
    }

    public static void obtenerEstabl (Context context) {
        elContexto = context;
        if (checkNetworkConnection(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, OBTENERESTBL_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        System.out.println("TAMAÑO--->"+jsonarray.length());
                        for(int i=0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String idest = java.net.URLDecoder.decode( jsonobject.getString("IDEST"),"UTF-8");
                            String nombre_est = java.net.URLDecoder.decode(jsonobject.getString("NOMBRE_EST"),"UTF-8");
                            String id_serv = java.net.URLDecoder.decode(jsonobject.getString("ID_SERV"),"UTF-8");
                            String dir_est = java.net.URLDecoder.decode(jsonobject.getString("DIR_EST"),"UTF-8");
                            String telefono_est = jsonobject.getString("TELEFONO_EST");
                            String email_est = jsonobject.getString("EMAIL_EST");
                            String lat_est = jsonobject.getString("LAT_EST");
                            String long_est = jsonobject.getString("LONG_EST");
                            String niv_precio = jsonobject.getString("NIV_PRECIO");
                            String calificacion = jsonobject.getString("CALIFICACION");

                            System.out.println("["+i+"]"+idest+"--"+nombre_est+"--"+id_serv+"--"+dir_est
                                    +"--"+telefono_est+"--"+email_est+"--"+lat_est+"--"+long_est+"--"+niv_precio+"--"+calificacion);
                            System.out.println("<----------------------------->");

                        }
                        notifyUser("LLEGO--ESTBL");

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

    private static void notifyUser(String message){
        Toast.makeText(elContexto, message, Toast.LENGTH_SHORT).show();
    }
}
