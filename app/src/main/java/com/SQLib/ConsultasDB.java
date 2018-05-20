package com.SQLib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.solvo.solvo.FunComentar;
import com.example.solvo.solvo.MenuPrincipal;
import com.example.solvo.solvo.RestablecerContra;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    public static final String OBTENERCOMENT_URL = "http://54.145.165.9/php-solvo/obtenerComentarios.php";
    public static final String INSERTARCOMENT_URL = "http://54.145.165.9/php-solvo/insertarComentario.php";
    public static final String INSERTARCALIF_URL = "http://54.145.165.9/php-solvo/insertarCalificacion.php";
    public static final String OBTENERCALIF_URL = "http://54.145.165.9/php-solvo/obtenerCalif.php";
    public static final String ACTUALIZARCALIF_URL = "http://54.145.165.9/php-solvo/actualizarCalificacionEst.php";
    public static final String OBTENERCONDSOLVO_URL = "http://54.145.165.9/php-solvo/obtenerConducSolvo.php";
    public static final String ACTUALIZARPUNTOSOLVO_URL = "http://54.145.165.9/php-solvo/actualizarPuntoSolvo.php";

    static Context elContexto;
    public static String obtenercon = "";
    public static boolean obtener = false;
    public static int cantidadCom = 0;
    public static int cantidadCalif = 0;



    public static boolean checkNetworkConnection(Context context){
        elContexto = context;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo!=null && networkInfo.isConnected()) {
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

    public static void insertarCalif(Context context, final String IDCALIF, final String CALIFICACION, final String CON_USER, final String ID_EST){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, INSERTARCALIF_URL, new Response.Listener<String>() {
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
                        if(Response.equals("Calificacion Insertada")){
                            System.out.println("ENTRO*****2-4");
                            //notifyUser("CALIFICACION INSERTADA EN BD");
                        }
                        if(Response.equals("Calificacion No Insertada")){
                            //notifyUser("CALIFICACION NO INSERTADA EN BD");
                        }
                        obtenerComent(elContexto);
                        obtenercalif(elContexto,CALIFICACION, ID_EST);



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
                    System.out.println("$$ "+IDCALIF+","+CALIFICACION+","+CON_USER+","+ID_EST);
                    Map<String,String> params = new HashMap<>();
                    params.put("IDCALIF",normalizarString(IDCALIF));
                    params.put("CALIFICACION",normalizarString(CALIFICACION));
                    params.put("CON_USER",normalizarString(CON_USER));
                    params.put("ID_EST",normalizarString(ID_EST));
                    //return super.getParams();
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");

        }
    }

    public static void insertarComent(Context context, final String IDCOM, final String COMENTARIO, final String COND_USER, final String ID_EST){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, INSERTARCOMENT_URL, new Response.Listener<String>() {
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
                        if(Response.equals("Comentario Insertado")){
                            System.out.println("ENTRO*****2-4");
                            //notifyUser("COMENTARIO INSERTADO EN BD");
                        }
                        if(Response.equals("Comentario No Insertado")){
                            //notifyUser("COMENTARIO NO INSERTADO EN BD");
                        }
                        obtenerComent(elContexto);



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
                    System.out.println("$$ "+IDCOM+","+COMENTARIO+","+COND_USER+","+ID_EST);
                    Map<String,String> params = new HashMap<>();
                    params.put("IDCOM",normalizarString(IDCOM));
                    params.put("COMENTARIO",normalizarString(COMENTARIO));
                    params.put("COND_USER",normalizarString(COND_USER));
                    params.put("ID_EST",normalizarString(ID_EST));
                    //return super.getParams();
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");

        }

    }

    public static void obtenerConducSolvo(final Context context, final String CONUSER){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, OBTENERCONDSOLVO_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println("Respondido---->"+response);
                        JSONArray jsonarray = new JSONArray(response);
                        for (int i = 0; i < jsonarray.length(); i++){
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //JSONObject jsonobject = new JSONObject(response);
                            String usuario = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("USUARIO")),"UTF-8").trim();
                            String nombrecompl = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("NOMBRECOMPL")),"UTF-8").trim();
                            String numcontacto = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("NUMCONTACTO")),"UTF-8").trim();
                            String fechanac = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("FECHANAC")),"UTF-8").trim();
                            String genero = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("GENERO")),"UTF-8").trim();
                            String correo = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("CORREO")),"UTF-8").trim();
                            String ciudad = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("CIUDAD")),"UTF-8").trim();
                            String puntos = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("PUNTOS")),"UTF-8").trim();
                            System.out.println("Conductor-->"+usuario+","+nombrecompl+","+numcontacto+","+fechanac+","+genero+","+correo+","+ciudad+","+puntos+";");
                            ConductorSolvo conductorSolvo = new ConductorSolvo(nombrecompl,usuario,numcontacto,fechanac,genero,correo,ciudad,puntos);
                            MenuPrincipal.conductorActual = conductorSolvo;
                        }

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
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("ENTRO**CONUSER--"+CONUSER);
                    Map<String,String> params = new HashMap<>();
                    params.put("CONUSER",CONUSER);
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");

        }
    }

    public static void actualizarPuntosSolvo(final Context context, final String CONUSER, final String CONPUNTOS){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ACTUALIZARPUNTOSOLVO_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("response");
                        if(Response != null){
                            System.out.println(Response);
                        }
                        if(Response.equals("Puntos Actualizados")){
                            //notifyUser("PUNTOS SOLVO ACTUALIZADOS");
                            MenuPrincipal.conductorActual.setPuntos(CONPUNTOS);
                        }
                        if(Response.equals("Puntos No Actualizados")){

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
                    params.put("CONUSER",CONUSER);
                    params.put("CONPUNTOS",CONPUNTOS);

                    //return super.getParams();
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");


        }
    }

    public static void actualizarCalf(final Context context, final String CalfEst, final String ID_EST){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ACTUALIZARCALIF_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("response");
                        if(Response != null){
                            System.out.println(Response);
                        }
                        if(Response.equals("Calificacion Est Actualizada")){
                            //System.out.println("ENTRO*****2-4");
                            //System.out.println("ESTADO DE CONDUCTOR CAMBIADO EN BD");
                            //notifyUser("CALIFICACION DE EST ACTUALIZADA EN BD");
                            //MenuPrincipal.db = new DatabaseHelper(elContexto);
                            //ConsultasDB.obtenerEstabl(elContexto, MenuPrincipal.db);



                            //notifyUser("LOS ESTABLECIMIENTOS SE ESTAN ACTUALIZANDO");
                            for(Establecimiento est: MenuPrincipal.estableList){
                                if(est.getIDEST().equals(ID_EST)){
                                    String Calf = CalfEst.replaceAll(",",".");
                                    System.out.println(Calf);
                                    float califE = Float.parseFloat(Calf);
                                    System.out.println("ACTUALIZAR--"+califE);
                                    est.setCALIFICACION(califE);
                                    MenuPrincipal.db.updateEstablecimiento(est);
                                }

                            }
                        }
                        if(Response.equals("CALIFICACION DE EST ACTUALIZADA EN BD")){

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
                    params.put("IDEST",ID_EST);
                    params.put("ESTCALIF",CalfEst);

                    //return super.getParams();
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");


        }
    }

    public static void obtenercalif(final Context context, final String CalfUsuario, final String ID_EST){
        float califEstList = (float) 0.0;
        for(Establecimiento est: MenuPrincipal.estableList){
            if(est.getIDEST().equals(ID_EST)){
                califEstList = est.getCALIFICACION();
            }

        }
        elContexto = context;
        if(checkNetworkConnection(context)){
            final float finalCalifEstList = califEstList;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, OBTENERCALIF_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println("Respuesta Cal->"+response);
                        JSONArray jsonarray = new JSONArray(response);
                        System.out.println("TAMAÑO--->"+jsonarray.length());
                        MenuPrincipal.listaComentarios.clear();
                        List<Float> califEstablSolvo = new ArrayList<>();
                        califEstablSolvo.add(finalCalifEstList);
                        for(int i=0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            float calf = Float.parseFloat(java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("CALFSOLVO")),"UTF-8").trim());
                            califEstablSolvo.add(calf);
                        }
                        float sumCalif =0;
                        for(float flo: califEstablSolvo){
                            System.out.println("Principal:"+ finalCalifEstList +"Usuario"+CalfUsuario+"-vs-"+flo);
                            sumCalif = sumCalif + flo;
                        }
                        float calFinal = sumCalif/califEstablSolvo.size();
                        System.out.println("CALIFICACION FINAL DE "+ID_EST+"-->"+calFinal+" Tam:"+califEstablSolvo.size());


                        //String strCalFinal = ""+calFinal;
                        String strCalFinal = String.format ("%.2f", calFinal);

                        actualizarCalf(elContexto,strCalFinal,ID_EST);

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
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("ENTRO*****3");
                    Map<String,String> params = new HashMap<>();
                    params.put("ID_EST",ID_EST);
                    //return super.getParams();
                    return params;
                }
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");
        }


    }

    public static void obtenerCantidadCalif(final Context context, final String CALIFICACION, final String CON_USER, final String ID_EST){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, INSERTARCALIF_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        for (int i = 0; i < jsonarray.length(); i++){
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            int cantidadCal = Integer.parseInt(jsonobject.getString("CANTIDAD").trim());
                            cantidadCalif = cantidadCal;
                            System.out.println("Cantidad Calificacion-->"+cantidadCom);
                            int idCalif = cantidadCalif + 1 ;
                            String IDCALIF = "CAL" + idCalif;
                            insertarCalif(context,IDCALIF,CALIFICACION,CON_USER,ID_EST);
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
                /*@Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("ENTRO*****3");
                    Map<String,String> params = new HashMap<>();
                    params.put("USUARIO",usuarioCond);
                    //return super.getParams();
                    return params;
                }*/
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");

        }

    }



    public static void  obtenCantidadComent(final Context context, final String COMENTARIO, final String COND_USER, final String ID_EST){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, INSERTARCOMENT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        for (int i = 0; i < jsonarray.length(); i++){
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            int cantidadComent = Integer.parseInt(jsonobject.getString("CANTIDAD").trim());
                            cantidadCom = cantidadComent;
                            System.out.println("Cantidad Comentarios-->"+cantidadCom);
                            int idcomen = cantidadCom + 1 ;
                            String IDCOM = "COM" + idcomen;
                            insertarComent(context,IDCOM,COMENTARIO,COND_USER,ID_EST);
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
                /*@Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("ENTRO*****3");
                    Map<String,String> params = new HashMap<>();
                    params.put("USUARIO",usuarioCond);
                    //return super.getParams();
                    return params;
                }*/
            };
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");

        }

    }

    public static void obtenerComent(final Context context){
        elContexto = context;

        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, OBTENERCOMENT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        System.out.println("TAMAÑO--->"+jsonarray.length());
                        MenuPrincipal.listaComentarios.clear();
                        //List<Establecimiento> estableList = new ArrayList<>();
                        for(int i=0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String idcom = java.net.URLDecoder.decode( quitarPorcentajes(jsonobject.getString("IDCOM")),"UTF-8").trim();
                            String comentario = java.net.URLDecoder.decode(quitarPorcentajes(jsonobject.getString("COMENTARIO")),"UTF-8").trim();
                            String cond_user = java.net.URLDecoder.decode(quitarPorcentajes(jsonobject.getString("COND_USER")),"UTF-8").trim();
                            String id_est = java.net.URLDecoder.decode(quitarPorcentajes(jsonobject.getString("ID_EST")),"UTF-8").trim();

                            // Establecimiento e = new Establecimiento(idest,nombre_est,id_serv,dir_est,telefono_est,email_est,lat_est,long_est,niv_precio,calificacion);
                            //estableList.add(e);
                            Comentario comen = new Comentario(idcom,comentario,cond_user,id_est);



                            System.out.println("["+(i+1)+"]------- "+idcom+","+comentario+","+cond_user+","+id_est);
                            System.out.println("<----------------------------->");
                            MenuPrincipal.listaComentarios.add(comen);
                            //db.insertEstablecimiento(idest,nombre_est,id_serv,dir_est,telefono_est,email_est,lat_est,long_est,niv_precio,calificacion);


                        }
                        //MenuPrincipal.estableList.addAll(db.getEstablecimientos());
                        //List<Establecimiento> establecimientos = db.getEstablecimientos();
                        //notifyUser("LLEGARON- "+ MenuPrincipal.listaComentarios.size()+" -COMEN");
                        //MenuPrincipal.imprimirLista(MenuPrincipal.estableList);
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
            });
            System.out.println("ENTRO*****4");
            MySingleton.getmInstance(context).addToRequestQue(stringRequest);
            System.out.println("ENTRO*****5");
        }
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
                        MenuPrincipal.estableList.clear();
                        //SQLiteDatabase datab = db.getWritableDatabase();
                        //String borrarQuery = "DELETE  * FROM " + Establecimiento.TABLE_NAME;
                        //System.out.println("Lo Retorno--"+datab.delete(Establecimiento.TABLE_NAME,"1",null));
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
                            float calificacion = Float.parseFloat(java.net.URLDecoder.decode((quitarPorcentajes(jsonobject.getString("CALIFICACION")).trim()),"UTF-8"));

                           // Establecimiento e = new Establecimiento(idest,nombre_est,id_serv,dir_est,telefono_est,email_est,lat_est,long_est,niv_precio,calificacion);
                            //estableList.add(e);



                            System.out.println("["+(i+1)+"]------- "+idest+","+nombre_est+","+id_serv+","+dir_est
                                    +","+telefono_est+","+email_est+","+lat_est+","+long_est+","+niv_precio+","+calificacion);
                            System.out.println("<----------------------------->");
                            //db.insertEstablecimiento(idest,nombre_est,id_serv,dir_est,telefono_est,email_est,lat_est,long_est,niv_precio,calificacion);
                            Establecimiento e = new Establecimiento(idest,nombre_est,id_serv,dir_est,telefono_est,email_est,lat_est,long_est,niv_precio,calificacion);
                            MenuPrincipal.estableList.add(e);

                        }
                        //MenuPrincipal.estableList.addAll(db.getEstablecimientos());
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
        cadena = cadena.replaceAll("%2C",".");
        return cadena;
    }

    private static void notifyUser(String message){
        Toast.makeText(elContexto, message, Toast.LENGTH_SHORT).show();
    }
}
