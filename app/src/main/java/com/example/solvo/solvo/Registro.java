package com.example.solvo.solvo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.solvo.awsandroid.AWSRegistryHandler;
import com.solvo.awsandroid.AWSLoginModel;

public class Registro extends AppCompatActivity implements View.OnClickListener, AWSRegistryHandler {

    AWSLoginModel awsLoginModel;
    //LINK Declaración de Derechos y  Responsabilidades SOLVO:
    // http://pegasus.javeriana.edu.co/~CIS1730CP08/docs/SOLVO-DTyR.pdf

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // instantiating AWSLoginModel(context, callback)
        awsLoginModel = new AWSLoginModel(Registro.this, Registro.this);

        findViewById(R.id.registerButton).setOnClickListener(this);
        //findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.confirmButton).setOnClickListener(this);
        findViewById(R.id.phoneU).setOnClickListener(this);
        findViewById(R.id.continuarButton).setOnClickListener(this);
        findViewById(R.id.condicionesTV).setOnClickListener(this);

    }


    @Override
    public void onRegisterSuccess(boolean mustConfirmToComplete) {
        if (mustConfirmToComplete) {
            Toast.makeText(Registro.this, "Almost done! Confirm code to complete registration", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Registro.this, "Registered! Login Now!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRegisterConfirmed() {
        Toast.makeText(Registro.this, "Registered! Login Now!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignInSuccess() {
        Registro.this.startActivity(new Intent(Registro.this, MenuPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void onFailure(int process, Exception exception) {
        exception.printStackTrace();
        String whatProcess = "";
        switch (process) {
            case AWSLoginModel.PROCESS_SIGN_IN:
                whatProcess = "Sign In:";
                break;
            case AWSLoginModel.PROCESS_REGISTER:
                whatProcess = "Registration:";
                break;
            case AWSLoginModel.PROCESS_CONFIRM_REGISTRATION:
                whatProcess = "Registration Confirmation:";
                break;
        }
        Toast.makeText(Registro.this, whatProcess + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                registerAction();
                break;
            case R.id.confirmButton:
                confirmAction();
                break;
            case R.id.loginButton:
                loginAction();
                break;
            case R.id.continuarButton:
                continuarAction();
                break;
            case R.id.condicionesTV:
                mostrarCondiciones();
                break;
        }
    }

    private void registerAction() {
        EditText nombreU = findViewById(R.id.NombreU);
        EditText phone = findViewById(R.id.phoneU);
        EditText userName = findViewById(R.id.registerUsername);
        EditText fechaNac = findViewById(R.id.FechaNacU);
        EditText genU = findViewById(R.id.GenU);
        EditText ciudadU = findViewById(R.id.CiudadU);
        EditText email = findViewById(R.id.registerEmail);
        EditText password = findViewById(R.id.registerPassword);


        // do register and handles on interface

        awsLoginModel.registerUser(userName.getText().toString(), email.getText().toString(), password.getText().toString(),phone.getText().toString(),nombreU.getText().toString(),
                fechaNac.getText().toString(),ciudadU.getText().toString(),genU.getText().toString());
    }

    private void confirmAction() {
        EditText confirmationCode = findViewById(R.id.confirmationCode);

        // do confirmation and handles on interface
        awsLoginModel.confirmRegistration(confirmationCode.getText().toString());
    }

    private void loginAction() {
        EditText userOrEmail = findViewById(R.id.loginUserOrEmail);
        EditText password = findViewById(R.id.loginPassword);

        // do sign in and handles on interface
        awsLoginModel.signInUser(userOrEmail.getText().toString(), password.getText().toString());
    }
    private void continuarAction() {
        startActivity(new Intent(Registro.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
    private void mostrarCondiciones(){
        //Uri uri = Uri.parse("http://pegasus.javeriana.edu.co/~CIS1730CP08/docs/SOLVO-DTyR.pdf#glossary");
        //Uri uri = Uri.parse("https://www.pdfescape.com/shared/?773762BA4F4D5FDB3720CC3525EF8D50D5831809B7ABA99C");
        Uri uri = Uri.parse("https://drive.google.com/file/d/15d01u_Et_SpI1ATtcNTiTPRlJZ0rWs57/view?usp=sharing");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

}
