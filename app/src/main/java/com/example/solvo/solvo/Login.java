package com.example.solvo.solvo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.regions.Regions;
import com.solvo.awsandroid.AWSLoginModel;
import com.solvo.awsandroid.AWSRegistryHandler;



public class Login extends AppCompatActivity implements View.OnClickListener, AWSRegistryHandler {

    AWSLoginModel awsLoginModel;
    public static Context contexta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        contexta = getApplicationContext();
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){

            }else{
                if((extras.getString("PB")).equals("N")){
                    progressBar.setVisibility(View.GONE);
                }else if((extras.getString("PB")).equals("Y")){
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }



        // instantiating AWSLoginModel(context, callback)
        awsLoginModel = new AWSLoginModel(Login.this, Login.this);
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.registroButton).setOnClickListener(this);
        findViewById(R.id.OlvidoContra).setOnClickListener(this);

    }

    public static Context getContext(){
        return contexta;
    }

    @Override
    public void onRegisterSuccess(boolean mustConfirmToComplete) {
        //
    }

    @Override
    public void onRegisterConfirmed() {
        //
    }

    @Override
    public void onSignInSuccess() {
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        Login.this.startActivity(new Intent(Login.this, MenuPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void onFailure(int process, Exception exception) {


        exception.printStackTrace();
        String whatProcess = "";
        switch (process) {
            case AWSLoginModel.PROCESS_SIGN_IN:
                whatProcess = "Sign In:";
                break;
        }
        if(whatProcess.equals("Sign In:")){
            Toast.makeText(getApplicationContext(),
                    "El correo o la contraseña no son correctos. Intenta nuevamente!!", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(Login.this, whatProcess + " Error->" + exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                loginAction();
                break;
            case R.id.registroButton:
                registrarAction();
                break;
            case R.id.OlvidoContra:
                olvidarContraAction();
                break;
        }
    }

    private void loginAction() {

        ProgressBar progressBar = findViewById(R.id.progressBar2);
        EditText userOrEmail = findViewById(R.id.loginUserOrEmail);
        EditText password = findViewById(R.id.loginPassword);
        progressBar.setVisibility(View.VISIBLE);

        if(userOrEmail.getText().toString().equals("") && password.getText().toString().equals("")) {
            // do sign in and handles on interface
            Toast.makeText(getApplicationContext(),
                    "Los valores están vacíos, intenta nuevamente!!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }else{
            awsLoginModel.signInUser(userOrEmail.getText().toString(), password.getText().toString());
            AWSMobileClient.getInstance().initialize(Login.this, new AWSStartupHandler() {
                @Override
                public void onComplete(AWSStartupResult awsStartupResult) {
                    IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
                    identityManager.resumeSession(Login.this, new StartupAuthResultHandler() {
                        @Override
                        public void onComplete(StartupAuthResult authResults) {
                            if (authResults.isUserSignedIn()) {


                                startActivity(new Intent(Login.this, MenuPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            } else {
                                Intent i = new Intent(Login.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra("PB","N");
                                startActivity(i);
                                //startActivity(new Intent(Login.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        }
                    }, 5000);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).execute();
        }
    }

    private void registrarAction() {
        System.out.println("ENTRO a REGISTRO");
        Intent i = new Intent(getBaseContext(), Registro.class);
        //i.putExtra("PersonID", personID);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        //startActivity(new Intent(Login.this, Registro.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    private void olvidarContraAction(){
        //Enviar al correo contraseña
        Toast.makeText(Login.this, "ESTA FUNCIÓN SE ENCUENTRA EN CONSTRUCCIÓN", Toast.LENGTH_LONG).show();
        startActivity(new Intent(Login.this, RestablecerContra.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }




}
