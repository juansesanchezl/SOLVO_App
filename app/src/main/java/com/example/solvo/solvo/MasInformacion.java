package com.example.solvo.solvo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MasInformacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_informacion);
        String id;
        String name;
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = null;
                name = null;
            }else {
                id = extras.getString("id");
                TextView textViewID = (TextView) findViewById(R.id.ID_Marcador);
                textViewID.setText(id);
                name = extras.getString("name");
                TextView textViewName = (TextView) findViewById(R.id.NomMrk);
                textViewName.setText(name);
            }
        }


    }
}
