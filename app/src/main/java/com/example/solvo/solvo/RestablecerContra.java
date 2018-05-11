package com.example.solvo.solvo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.correoE.enviarCorreo;
import com.dynamodb.ActualizarTabla;
import com.dynamodb.Conductor;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.dynamodb.ManagerClass;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RestablecerContra extends AppCompatActivity {

    final static String LOG_TAG = RestablecerContra.class.getName();
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    final static String IDENTITY_POOL_ID = "us-east-1:a22f778a-533b-4a40-8d52-78ac17263a31";
    private CognitoCachingCredentialsProvider credentialsProvider;
    private static final String TAG = RestablecerContra.class.getSimpleName();


    //AWS
    AmazonDynamoDBClient ddbClient;
    //CognitoCachingCredentialsProvider credentialsProvider;
    DynamoDBMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contra);
        // init AWS
        System.out.println("ENTRO***1");
        /*ManagerClass managerClass = new ManagerClass();
        credentialsProvider = managerClass.getCredentialsProvider(RestablecerContra.this);
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(), // Context
                IDENTITY_POOL_ID, // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        mapper = new DynamoDBMapper(ddbClient);
        */


        final EditText etCorreo = (EditText) findViewById(R.id.etCorreo);
        Button btnRestaContra = (Button) findViewById(R.id.btnRestContra);

        btnRestaContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etCorreo.getText().toString().isEmpty()){
                    if (!validarEmail(etCorreo.getText().toString())){
                        etCorreo.setError("Email no válido");
                    }else {
                        sendEmail(etCorreo.getText().toString().trim());
                    }

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

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean validateCorreo(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    protected void sendEmail(String correoUs) {
        Object dataTransfer[] = new Object[3];
        //dataTransfer[0] = "juans.sanchezlopez@gmail.com";
        dataTransfer[0] = correoUs;
        dataTransfer[1] = "jasldkjasldkjasldkaldkja";
        EnviarRestContra enviarRestContra =  new EnviarRestContra();
        enviarRestContra.execute(dataTransfer);
    }


   private class EnviarRestContra extends AsyncTask<Object, Integer, Void> {

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

    private void notifyUser(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
