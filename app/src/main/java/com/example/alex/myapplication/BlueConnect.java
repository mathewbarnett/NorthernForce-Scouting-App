package com.example.alex.myapplication;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import java.util.UUID;

import java.util.ArrayList;
//WHEN RUN ON DIFFERENT DEVICE WONT STOP CRASHING/ UTILS.MACADDRESS IS SUSPECTED
/**
 * Created by Oombliocarius on 10/22/15.
 */
public class BlueConnect {
    int q = 0;
   MainActivity mA;
    UUID j = null;
    ArrayList<BluetoothSocket> connections = new ArrayList<BluetoothSocket>(7);
    String android_id;
    public BlueConnect() {

    }

    public BlueConnect(MainActivity the, UUID uuid, Context leggo) {


    }
    String thisAddress = null;
    public String run(MainActivity the, UUID uuid, Context leggo) {
        mA = the;
        String b = "";

        Utils.getMACAddress(null);
        j = uuid;
        android_id = Settings.Secure.getString(leggo.getContentResolver(), Settings.Secure.ANDROID_ID);
        String thisAddress = Utils.getMACAddress(null);
        BluetoothAdapter bl = BluetoothAdapter.getDefaultAdapter();

        Log.v("Mac Address", "B" + thisAddress);

        if (android_id.equalsIgnoreCase("ce5798be02b59464")) {

            Log.v("Mac Address", "MASTER");
            b = "master";
            mA.setDiscoverable();
            Listener listen = new Listener();
            listen.setUUID(j);
            Thread t = new Thread(listen);
            t.start();
            //  NetworkScanner scanner = new NetworkScanner();
        }

//

        if (!android_id.equalsIgnoreCase("ce5798be02b59464")) {

            Log.v("Mac Address", "servant");
            b = "servant";



            // Log.v("Mac Address", Integer.toString(bl.getScanMode()));
         //   Aggro agr = new Aggro(j, mA);
          //      Thread t = new Thread(agr);
          //      t.start();






        }
    return b;
    }


    }



