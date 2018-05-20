package com.example.solvo.solvo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.SQLib.ConsultasDB;
import com.solvo.awsandroid.AWSRegistryHandler;
import com.solvo.awsandroid.AWSLoginModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener, AWSRegistryHandler {

    AWSLoginModel awsLoginModel;
    String generoU = "No Disponible";
    String FechaNacUs = "No Disponible";
    public static Context contexta;
    //Variables Usuario
    String userU ;
    String emailU ;
    String passwordU ;
    String phoneU ;
    String nameU;
    String fechaNacU ;
    String ciudadUs;
    String genero ;


    //LINK Declaración de Derechos y  Responsabilidades SOLVO:
    // http://pegasus.javeriana.edu.co/~CIS1730CP08/docs/SOLVO-DTyR.pdf

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        contexta = getApplicationContext();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // instantiating AWSLoginModel(context, callback)
        awsLoginModel = new AWSLoginModel(Registro.this, Registro.this);

        findViewById(R.id.registerButton).setOnClickListener(this);
        //findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.confirmButton).setOnClickListener(this);
        findViewById(R.id.phoneU).setOnClickListener(this);
        //findViewById(R.id.continuarButton).setOnClickListener(this);
        findViewById(R.id.condicionesTV).setOnClickListener(this);
        findViewById(R.id.btnFechaNac).setOnClickListener(this);
        final RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radiogrp);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = mRadioGroup.getCheckedRadioButtonId();
                View radioButton = mRadioGroup.findViewById(id);
                if (radioButton.getId() == R.id.rbtnM) {
                    generoU = "Masculino";

                } else if (radioButton.getId() == R.id.rbtnF) {
                    generoU = "Femenino";

                }
            }
        });

    }

    public static Context getContext(){
        return contexta;
    }

    @Override
    public void onRegisterSuccess(boolean mustConfirmToComplete) {
        if (mustConfirmToComplete) {
            Toast.makeText(Registro.this, "Almost done! Confirm code to complete registration", Toast.LENGTH_LONG).show();
            //REGISTRAR USUARIO EN BD

            ConsultasDB.insertarConductorBD(Registro.this,userU, emailU, passwordU , phoneU, nameU, fechaNacU, ciudadUs, genero);
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
            /*case R.id.continuarButton:
                continuarAction();
                break;*/
            case R.id.condicionesTV:
                mostrarCondiciones();
                break;
            case R.id.btnFechaNac:
                obtenerFecha();
                break;
        }
    }

    private void obtenerFecha(){

        //URL https://01luisrene.com/implementar-time-picker-y-date-picker-android/

        //Calendario para obtener fecha & hora
        Calendar c = Calendar.getInstance();

        //Variables para obtener la fecha
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int anio = c.get(Calendar.YEAR);


        //Widgets
        EditText etFecha;
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                //final String FechaUsuarioNac = diaFormateado + "-" + mesFormateado + "-" + year;
                final String FechaUsuarioNac = year + "-" + mesFormateado + "-" + diaFormateado;
                FechaNacUs = FechaUsuarioNac;
                Button btnFechaNac = (Button) findViewById(R.id.btnFechaNac);
                btnFechaNac.setText(FechaNacUs);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();


    }

    private void registerAction() {
        EditText nombreU = findViewById(R.id.NombreU);
        EditText phone = findViewById(R.id.phoneU);
        EditText userName = findViewById(R.id.registerUsername);
        //EditText fechaNac = findViewById(R.id.FechaNacU);
        //EditText genU = findViewById(R.id.GenU);
        EditText ciudadU = findViewById(R.id.CiudadU);
        EditText email = findViewById(R.id.registerEmail);
        EditText password = findViewById(R.id.registerPassword);

        userU = userName.getText().toString().trim();
        emailU = email.getText().toString().trim();
        passwordU = password.getText().toString().trim();
        phoneU = "+57"+phone.getText().toString().trim();
        nameU = nombreU.getText().toString().trim();
        fechaNacU = FechaNacUs.trim();
        ciudadUs = ciudadU.getText().toString().trim();
        genero = generoU.trim();

        // do register and handles on interface
        System.out.println("Fecha:"+FechaNacUs+"Genero"+generoU);
        awsLoginModel.registerUser(userU, emailU,passwordU ,phoneU,nameU,
                fechaNacU,ciudadUs,genero);
    }

    private void confirmAction() {
        //EditText confirmationCode = findViewById(R.id.confirmationCode);
        // do confirmation and handles on interface
        //awsLoginModel.confirmRegistration(confirmationCode.getText().toString());
        Intent intent = new Intent(Registro.this, confirmationCode.class);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(awsLoginModel);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            intent.putExtra("aws",yourBytes);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        startActivity(new Intent(Registro.this, confirmationCode.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


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
        /*Uri uri = Uri.parse("https://drive.google.com/file/d/15d01u_Et_SpI1ATtcNTiTPRlJZ0rWs57/view?usp=sharing");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);*/
        startActivity(new Intent(Registro.this, Derechos.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

}
