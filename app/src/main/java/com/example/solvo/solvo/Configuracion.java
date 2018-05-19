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
        SeekBar skbarAct = (SeekBar)findViewById(R.id.skbarRadio);
        skbarAct.setProgress((int)(MenuPrincipal.Kilometros_Radio/10));
        skbarAct.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int MIN = 1;
                TextView tvTexto = (TextView) findViewById(R.id.txRadioShow);
                if (progress < MIN) {
                    tvTexto.setText("Radio de Búsqueda Actual:"+MenuPrincipal.Kilometros_Radio+" Km");
                } else {
                    MenuPrincipal.Kilometros_Radio = 14*progress;
                }
                tvTexto.setText("Radio de Búsqueda Actual:"+MenuPrincipal.Kilometros_Radio+" Km");
                //value.setText(" Time Interval (" + seektime + " sec)");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                notifyUser("Valor de Radio Actualizado a:"+MenuPrincipal.Kilometros_Radio);

            }
        });
        Button btnActualizar = (Button) findViewById(R.id.btnRegresar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SeekBar skbarAct = (SeekBar)findViewById(R.id.skbarRadio);
                //MenuPrincipal.Kilometros_Radio = 100*skbarAct.getProgress();
                //notifyUser("Valor:"+skbarAct.getProgress());
                //TextView tvTexto = (TextView) findViewById(R.id.txRadioShow);
                //tvTexto.setText("Radio de Búsqueda Actual:"+MenuPrincipal.Kilometros_Radio+" Km");
                Configuracion.this.finish();

            }
        });
    }


    private void notifyUser(String message){

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
