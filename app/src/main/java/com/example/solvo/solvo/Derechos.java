package com.example.solvo.solvo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Derechos extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://www.youtube.com/watch?v=-Ld1IoOF_uk
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derechos);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("SOLVO-DTyR.pdf").load();
        //new RetrievePDFStream().execute("http://pegasus.javeriana.edu.co/~CIS1730CP08/docs/SOLVO-DTyR.pdf");

    }
/*
    class RetrievePDFStream extends AsyncTask<String, Void, InputStream>{


        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try{
                URL url = new URL (strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() ==200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream){
            pdfView.fromStream(inputStream).load();
        }

    }*/
}
