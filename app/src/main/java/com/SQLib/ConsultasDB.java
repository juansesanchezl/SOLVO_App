package com.SQLib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.solvo.solvo.MenuPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsultasDB {

    public static final String REGISTRAR_URL = "http://54.145.165.9/php-solvo/registrar.php";
    static Context elContexto;


    public static boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return(networkInfo!=null && networkInfo.isConnected());
    }

    public static void insertarConductorBD(Context context, final String userU, final String emailU, final String passwordU, final String phoneU, final String nameU, final String fechaNacU, final String ciudadUs, final String genero){

        elContexto = context;
        System.out.println("ENTRO*****1");
        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRAR_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
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
                    params.put("USUARIO",userU);
                    params.put("NOMBRECOMPL",nameU);
                    params.put("NUMCONTACTO",phoneU);
                    params.put("FECHANAC",fechaNacU);
                    params.put("GENERO",genero);
                    params.put("CORREO",emailU);
                    params.put("PASSWORD",passwordU);
                    params.put("CIUDAD",ciudadUs);

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

    private static void notifyUser(String message){
        Toast.makeText(elContexto, message, Toast.LENGTH_SHORT).show();
    }
}
