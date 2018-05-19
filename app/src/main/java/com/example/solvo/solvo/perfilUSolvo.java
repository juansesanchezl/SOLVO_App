package com.example.solvo.solvo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class perfilUSolvo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usolvo);
        TextView tvNombre = (TextView) findViewById(R.id.tvNombre);
        TextView tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        TextView tvNumero = (TextView) findViewById(R.id.tvNumero);
        TextView tvFeNac = (TextView) findViewById(R.id.tvFeNac);
        TextView tvGenero = (TextView) findViewById(R.id.tvGenero);
        TextView tvCiudad = (TextView) findViewById(R.id.tvCiudad);
        TextView tvCorreo = (TextView) findViewById(R.id.tvCorreo);
        TextView tvPuntoSolvo = (TextView) findViewById(R.id.tvPuntoSolvo);
        if(MenuPrincipal.conductorActual != null) {
            tvNombre.setText("Nombre: "+MenuPrincipal.conductorActual.getNombre());
            tvUsuario.setText("Usuario: "+MenuPrincipal.conductorActual.getUsuario());
            tvNumero.setText("Numero: "+MenuPrincipal.conductorActual.getNumContacto());
            tvFeNac.setText("Fecha de Nacimiento: "+MenuPrincipal.conductorActual.getFechaNac());
            tvGenero.setText("Género: "+MenuPrincipal.conductorActual.getGenero());
            tvCiudad.setText("Ciudad: "+MenuPrincipal.conductorActual.getCiudad());
            tvCorreo.setText("Correo: "+MenuPrincipal.conductorActual.getCorreoe());
            tvPuntoSolvo.setText("Puntos Solvo: "+MenuPrincipal.conductorActual.getPuntos());
        }else{
            notifyUser("NO HAY UN USUARIO CARGADO");
        }
    }

    private void notifyUser(String message){

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
