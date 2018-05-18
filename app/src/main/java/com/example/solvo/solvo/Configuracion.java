package com.example.solvo.solvo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Configuracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        TextView tvTexto = (TextView) findViewById(R.id.txRadioShow);
        tvTexto.setText("Radio de Búsqueda Actual:"+MenuPrincipal.Kilometros_Radio+" Km");
        Button btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeekBar skbarAct = (SeekBar)findViewById(R.id.skbarRadio);
                MenuPrincipal.Kilometros_Radio = 100*skbarAct.getProgress();
                notifyUser("Valor:"+skbarAct.getProgress());
                TextView tvTexto = (TextView) findViewById(R.id.txRadioShow);
                tvTexto.setText("Radio de Búsqueda Actual:"+MenuPrincipal.Kilometros_Radio+" Km");
            }
        });
    }


    private void notifyUser(String message){

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
