package com.example.solvo.solvo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.solvo.awsandroid.AWSLoginModel;
import com.solvo.awsandroid.AWSRegistryHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class confirmationCode extends AppCompatActivity implements AWSRegistryHandler {

    AWSLoginModel awsLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_code);
        Button btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        // instantiating AWSLoginModel(context, callback)
        awsLoginModel = new AWSLoginModel(confirmationCode.this, confirmationCode.this);
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){

            }else {
                byte[] bytes = extras.getByteArray("aws");
                if(convertirAWS(bytes)!=null) {
                    System.out.println("ENTRO AWS - Convertido");
                    awsLoginModel = convertirAWS(bytes);
                }
            }
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText confirmationCode = (EditText) findViewById(R.id.codigoTXT);
                String codigo = confirmationCode.getText().toString();
                if(!codigo.isEmpty()) {
                    System.out.println("codigo-->"+codigo);
                    awsLoginModel.confirmRegistration(codigo.trim());
                }else{
                    Toast.makeText(confirmationCode.this, "EL CAMPO ESTA VACIO", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(confirmationCode.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
    }

    private AWSLoginModel convertirAWS(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            AWSLoginModel awss = (AWSLoginModel) in.readObject();
            return awss;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }

    @Override
    public void onRegisterSuccess(boolean mustConfirmToComplete) {

    }

    @Override
    public void onRegisterConfirmed() {
        Toast.makeText(confirmationCode.this, "Esta Registrado, Inicie Sesión!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignInSuccess() {

    }

    @Override
    public void onFailure(int process, Exception exception) {
        Toast.makeText(confirmationCode.this, process+"--"+exception.toString(), Toast.LENGTH_LONG).show();
    }
}
