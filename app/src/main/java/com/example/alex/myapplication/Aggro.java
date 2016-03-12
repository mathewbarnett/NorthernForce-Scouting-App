package com.example.alex.myapplication;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Oombliocarius on 12/17/15.
 */
public class Aggro implements Runnable {
    private UUID ui;
    BluetoothSocket bs;
    OutputStream os;
    PrintStream haha;
    ControlledEnterDataActivity ced;
    BluetoothDevice bD;





    public Aggro(UUID u, ControlledEnterDataActivity ceda, BluetoothDevice bd) {
        ced = ceda;
        ui  = u;
        bD = bd;



    }

    public void run() {



//
        try {
            BlueConnect.setbD(bD);
            Log.v("Mac Address", "Aggro Started");
          //  bD.createBond();
            Log.v("Mac Address", pairDevice(bD) + "  PAIRING STATUS");
            Glib glib = new Glib(ui, ced, bD);
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
