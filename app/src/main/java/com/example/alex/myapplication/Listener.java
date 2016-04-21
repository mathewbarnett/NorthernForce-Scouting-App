package com.example.alex.myapplication;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Oombliocarius on 11/4/15.
 */
public class Listener implements Runnable {
    UUID uuid = null;
    BluetoothServerSocket bSS;
    BluetoothSocket bS;
    InputStream in;
    boolean  stopWorker;
    int readBufferPosition;
    byte[] readBuffer;
    Thread workerThread;

    public void setCed(ControlledEnterDataActivity ced) {
        this.ced = ced;
    }

    ControlledEnterDataActivity ced;

    public void run() {

        BluetoothAdapter bL  = BluetoothAdapter.getDefaultAdapter();
        try {
            bSS = bL.listenUsingRfcommWithServiceRecord("Server", uuid);
            Log.v("Mac Address", "Listening");
            bS = bSS.accept();
            Log.v("Mac Address", "Connection accepted");
            //   bS.connect();
            in =   bS.getInputStream();
            Log.v("Mac Address", "Sauron's Land");
            beginListenForData();
            //   Updater up = new Updater(in);
            //   Thread t = new Thread(up);
            //  t.start();

        }
        catch(Exception e) {

        }




    }

    public void setUUID(UUID id) {
        uuid = id;
    }










    void beginListenForData()
    {
        Log.v("Mac Address", "Devil's Door");
        stopWorker = false;

        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                Log.v("Mac Address", "STARTED");
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = in.available();
                        //Log.v("Mac Address", "BYTES: " + bytesAvailable);
                        if(bytesAvailable > 0)
                        {
                            Log.v("Mac Address", "WE'RE UP IN THIS");
                            byte[] packetBytes = new byte[in.available()];
                            //
                            // String data = new String(packetBytes, "UTF-8");
                            Log.v("Mac Address", "AVAILABLE: " + in.available());
                            Log.v("Mac Address", Arrays.toString(packetBytes));
                            //   in.read(packetBytes);
                            ObjectInputStream obin = new ObjectInputStream(in);
                            Object obo = null;
                            try {
                                obo = obin.readObject();
                            }
                            catch(Exception e) {

                            }
                            byte[] hope = (byte[]) obo;
                            Log.v("Mac Address", Arrays.toString(hope));


                            //   Log.v("Mac Address", String.valueOf(baos.size()));
                            ByteArrayInputStream bi = new ByteArrayInputStream(hope);
                            ZipInputStream zis = new ZipInputStream(bi);
                            Log.v("Mac Address", "HOPE: " + zis.available());
                            //      zis.getNextEntry();
                            //     byte[] results = null;
                            //     zis.read(results, 0, 0);
                            //     String please = new String(results, "UTF-8");
                            //     Log.v("Mac Address", please);

                            ZipEntry entry;
                            try {
                                while ((entry = zis.getNextEntry()) != null) {
                                    String s = String.format("Entry: %s len %d added %TD",
                                            entry.getName(), entry.getSize(),
                                            new Date(entry.getTime()));
                                    Log.v("Mac Address", "THIS FAR");
                                    // ZipFile zipFile = new ZipFile("text.zip");
                                    Log.v("Mac Address", "THIS FAR 1");
                                    ObjectInputStream ino = new ObjectInputStream(zis);
                                    Object ob = ino.readObject();

                                    Log.v("Mac Address", ob.toString());
                                    if(ob instanceof ConfigEntry) {
                                        ConfigEntry con = (ConfigEntry) ob;
                                        Log.v("Mac Address", con.getText());
                                    }
                                    if(ob instanceof SQLiteDatabase) {
                                        SQLiteDatabase con = (SQLiteDatabase) ob;
                                        Log.v("Mac Address", con.toString());
                                    }
                                    if(ob instanceof Cursor) {
                                        Cursor con = (Cursor) ob;
                                        Log.v("Mac Address", con.toString());
                                    }
                                    if(ob instanceof String) {
                                        String str = (String) ob;
                                        Log.v("Mac Address", str);
                                    }
                                    if(ob instanceof SubmissionData) {
                                        SubmissionData str = (SubmissionData) ob;
                                        Log.v("Mac Address", str.getData());
                                        UIDatabaseInterface.mergeToDatabase(str.getData());
                                    }

                                    new Thread()
                                    {
                                        public void run()
                                        {
                                            ced.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    //Do your UI operations like dialog opening or Toast here
                                                    ced.status.setText("Data received");
                                                    AlertDialog alertDialog = new AlertDialog.Builder(ced).create();
                                                    alertDialog.setTitle("Alert");
                                                    alertDialog.setMessage("Bluetooth data transfer completed");
                                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                    alertDialog.show();

                                                }
                                            });
                                        }
                                    }.start();





                                }
                            }
                            catch(Exception e) {
                                Log.v("Mac Address", "OH BOI");
                                StringWriter errors = new StringWriter();
                                e.printStackTrace(new PrintWriter(errors));
                                Log.v("Mac Address", errors.toString());
                                new Thread()
                                {
                                    public void run()
                                    {
                                        ced.runOnUiThread(new Runnable() {
                                            public void run() {
                                                //Do your UI operations like dialog opening or Toast here
                                                ced.status.setText("Data transfer failed");
                                                AlertDialog alertDialog = new AlertDialog.Builder(ced).create();
                                                alertDialog.setTitle("Alert");
                                                alertDialog.setMessage("Bluetooth data transfer failed");
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                alertDialog.show();

                                            }
                                        });
                                    }
                                }.start();

                            }



                        }
                    }
                    catch (IOException ex)
                    {

                        new Thread()
                        {
                            public void run()
                            {
                                ced.runOnUiThread(new Runnable() {
                                    public void run() {
                                        //Do your UI operations like dialog opening or Toast here
                                        ced.status.setText("Data transfer failed");
                                        AlertDialog alertDialog = new AlertDialog.Builder(ced).create();
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Bluetooth data transfer failed");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        alertDialog.show();

                                    }
                                });
                            }
                        }.start();

                        StringWriter errors = new StringWriter();
                        ex.printStackTrace(new PrintWriter(errors));
                        Log.v("Mac Address", errors.toString());
                        Log.v("Mac Address", "Uprising Failed");
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    public static Object deserialize(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        }
        catch (Exception e) {

        }
        return null;
    }


}

