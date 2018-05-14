package com.correoE;

import android.os.AsyncTask;

public class EnviarRestContra extends AsyncTask<Object, Integer, Void> {

    String correoUsuario;
    String passEncontrada;
    protected void onProgressUpdate() {
        //called when the background task makes any progress
    }

    @Override
    protected Void doInBackground(Object... objects) {
        correoUsuario = (String) objects[0].toString();
        passEncontrada = (String) objects[1].toString();
        enviarCorreo.enviarCorreoE(correoUsuario,passEncontrada);
        return null;
    }

    protected void onPreExecute() {
        //called before doInBackground() is started
    }
    protected void onPostExecute() {
        //called after doInBackground() has finished
    }


}