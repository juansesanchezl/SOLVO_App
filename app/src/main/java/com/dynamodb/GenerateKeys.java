package com.dynamodb;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateKeys {
    Context context;
    ArrayList<HashMap<String, String>> keysholder = new ArrayList<HashMap<String, String>>();
    List<S3ObjectSummary> s3ObjectSummaries;
    GenerateKeys(Context context){
        this.context = context;
    }

    private class downloadKeys extends AsyncTask<Void,Void,Void>{

        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context,"Loading", "Downloading Keys");
        }

        @Override
        protected Void doInBackground(Void... params) {
            ManagerClass managerClass = new ManagerClass();
            managerClass.getCredentialsProvider(context);
            AmazonS3Client s3Client = managerClass.inits3clients(context);
            s3ObjectSummaries = s3Client.listObjects(Utils.mybucket).getObjectSummaries();
            int i = 0;
            System.out.println("-----S3ObjectSummaries-----");
            for(S3ObjectSummary summary : s3ObjectSummaries){
                HashMap<String,String> maps = new HashMap<>();
                maps.put("key",summary.getKey());
                System.out.println("Llave #"+i+"-->"+summary.getKey());
                keysholder.add(maps);
                i++;
            }

            return null;
        }

        @Override
        protected  void  onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }

}
