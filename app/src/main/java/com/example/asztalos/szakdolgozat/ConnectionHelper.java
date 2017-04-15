package com.example.asztalos.szakdolgozat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectionHelper {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean result = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if(!result){
            Toast.makeText(context, "Ellenőrizd az internetkapcsolatot!",Toast.LENGTH_SHORT).show();
        }

        return result;
    }

}

