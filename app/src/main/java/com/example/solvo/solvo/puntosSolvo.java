package com.example.solvo.solvo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class puntosSolvo extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos_solvo);
        context = getApplicationContext();
        Button btnRest = (Button) findViewById(R.id.btnRest);
        Button btnPark = (Button) findViewById(R.id.btnPark);
        Button btnEst = (Button) findViewById(R.id.btnEst);
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyUser("ESTA OPCIÓN SE ENCUENTRA EN CONSTRUCCIÓN");
            }
        });
        btnPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyUser("ESTA OPCIÓN SE ENCUENTRA EN CONSTRUCCIÓN");
            }
        });
        btnEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyUser("ESTA OPCIÓN SE ENCUENTRA EN CONSTRUCCIÓN");
            }
        });
    }

    private void notifyUser(String message){

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
