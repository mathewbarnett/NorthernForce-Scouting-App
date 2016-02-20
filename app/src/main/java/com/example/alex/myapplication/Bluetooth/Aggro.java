package com.example.alex.myapplication.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.lang.reflect.Method;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.UUID;

import com.example.alex.myapplication.MainActivity;

/**
 * Created by Oombliocarius on 12/17/15.
 */
public class Aggro implements Runnable {
    private UUID ui;
    BluetoothSocket bs;
    OutputStream os;
    PrintStream haha;
    MainActivity ma;
    BluetoothDevice bD;





    public Aggro(UUID u, MainActivity mA, BluetoothDevice bd) {
        ma = mA;
        ui  = u;
        bD = bd;



    }

    public void run() {



//
        try {

            Log.v("Mac Address", "Aggro Started");
          //  bD.createBond();
            Log.v("Mac Address", pairDevice(bD) + "  PAIRING STATUS");
            Glib glib = new Glib(ui, ma, bD);
            Thread to = new Thread(glib);
            to.run();

        }
        catch(Exception e) {

        }





    }

    public void Aggr() {

    }

    public boolean pairDevice(BluetoothDevice device) {
        try {

                Method createBond = BluetoothDevice.class.getMethod("createBond");
            Log.v("Mac Address", String.valueOf((boolean) createBond.invoke(device)) + "OOOOOOOOOO");
       //     return (boolean) createBond.invoke(device);

        }
        catch (Exception e) {
        Log.v("Mac Address", "Catch Called");
           e.printStackTrace();
            return false;
        }
        return false;
    }



}
