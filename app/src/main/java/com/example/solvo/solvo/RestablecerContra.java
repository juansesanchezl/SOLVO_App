package com.example.solvo.solvo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.NewPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.regions.Regions;

public class RestablecerContra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contra);
        final EditText etCorreo = (EditText) findViewById(R.id.etCorreo);
        Button btnRestaContra = (Button) findViewById(R.id.btnRestContra);
        btnRestaContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etCorreo.getText().toString().isEmpty()){


                }else{
                    Toast.makeText(RestablecerContra.this, "EL CAMPO ESTA VACIO", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button btnIniciar = (Button) findViewById(R.id.btnIni);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestablecerContra.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
    }
}
