package com.example.solvo.solvo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class PagPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_principal);

        AWSMobileClient.getInstance().initialize(PagPrincipal.this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
                identityManager.resumeSession(PagPrincipal.this, new StartupAuthResultHandler() {
                    @Override
                    public void onComplete(StartupAuthResult authResults) {
                        if (authResults.isUserSignedIn()) {
                            startActivity(new Intent(PagPrincipal.this, MenuPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            startActivity(new Intent(PagPrincipal.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }
                }, 3000);
            }
        }).execute();


    }
}
