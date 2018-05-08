package com.example.solvo.solvo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.dynamodb.ActualizarTabla;
import com.dynamodb.Conductor;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.dynamodb.ManagerClass;


public class RestablecerContra extends AppCompatActivity {

    final static String LOG_TAG = RestablecerContra.class.getName();

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
        ManagerClass managerClass = new ManagerClass();
        credentialsProvider = managerClass.getCredentialsProvider(RestablecerContra.this);
        /*credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(), // Context
                IDENTITY_POOL_ID, // Identity Pool ID
                Regions.US_EAST_1 // Region
        );*/
        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        mapper = new DynamoDBMapper(ddbClient);



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

   /* private class ActualizarTabla extends AsyncTask<Void,Integer,Integer>{


        @Override
        protected Integer doInBackground(Void... params) {

            ManagerClass managerClass = new ManagerClass();
            CognitoCachingCredentialsProvider credentialsProvider = managerClass.getCredentialsProvider(RestablecerContra.this);
            Conductor conductor = new Conductor();
            conductor.setNombre("Maria");
            conductor.setCorreoE("maria123@gmail.com");
            conductor.setUsername("maria123");
            if (credentialsProvider != null && conductor != null) {
                DynamoDBMapper dynamoDBMapper = managerClass.initDynamoClient(credentialsProvider);
                dynamoDBMapper.save(conductor);
            } else {
                return 2;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 1){
                notifyUser("Todo Bien Todo Bien");
            }else if(integer  ==  2){
                notifyUser("Algo Salio Mal");
            }
        }
    }



    private void loadDataFromDbToTextView(final String username){
        new AsyncTask<Void, Void, Conductor>() {
            @Override
            protected Conductor doInBackground(final Void... params) {

                Conductor conductor;

                conductor = mapper.load(Conductor.class, username);

                return conductor;
            }

            @Override
            protected void onPostExecute(Conductor conductor){
                //resultLoad.setText(userARN.getEndpointArn());
                System.out.println(conductor.getCorreoE()+"-"+conductor.getUsername()+"-"+conductor.getNombre());
            }
        }.execute();
    }

    private void saveDataToDatabase(final String CorreoE, final String Username, final String Nombre){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(final Void... params) {

                Conductor conductor = new Conductor();
                conductor.setCorreoE(CorreoE);
                conductor.setUsername(Username);
                conductor.setNombre(Nombre);
                mapper.save(conductor);

                return null;
            }

            @Override
            protected void onPostExecute(String error){
                notifyUser("Awesome!");
                return;
            }
        }.execute();
    }
*/
    private void notifyUser(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
