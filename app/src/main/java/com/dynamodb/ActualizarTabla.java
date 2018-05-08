package com.dynamodb;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.example.solvo.solvo.RestablecerContra;

public class ActualizarTabla extends AsyncTask<Object,Integer,Integer> {

    Context context;

    @Override
    protected Integer doInBackground(Object... objects) {

        context = (Context) objects[0];

        ManagerClass managerClass = new ManagerClass();
        CognitoCachingCredentialsProvider credentialsProvider = managerClass.getCredentialsProvider(context);
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
            notifyUser("Todo Bien Todo Bien",context);
        }else if(integer  ==  2){
            notifyUser("Algo Salio Mal",context);
        }
    }

    private void notifyUser(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}