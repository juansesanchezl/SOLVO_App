package com.SQLib;

import android.net.ConnectivityManager;

public class Conduct {

    private String Name;
    private int Sync_status;

    Conduct(String Name, int Sync){
        this.setName(Name);
        this.setSync_status(Sync);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSync_status() {
        return Sync_status;
    }

    public void setSync_status(int sync_status) {
        Sync_status = sync_status;
    }


}
