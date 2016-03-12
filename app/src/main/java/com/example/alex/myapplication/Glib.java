package com.example.alex.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Oombliocarius on 1/26/16.
 */
public class Glib implements Runnable {


    BluetoothSocket bs, temp;
    OutputStream os;
    PrintStream haha;

    ControlledEnterDataActivity ced;
    BluetoothDevice bD;
    private UUID ui;
    int failed = 2;
    Object[] toWrite;
    SQLiteDatabase db;
    Cursor c;
    Thread communicationThread = null;


    public Glib(UUID u, ControlledEnterDataActivity ceda, BluetoothDevice bd) {


        ced = ceda;
        ui = u;
        bD = bd;


    }


    public void run() {


        ArrayList<String> l = new ArrayList<String>(10);

        BluetoothAdapter bluetoothAdapter
                = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            int o = 0;
            for (BluetoothDevice device : pairedDevices) {
                String deviceBTAdd = device.getAddress();
                Log.v("Mac Address", "PAIRED DEVICE: " + device.getAddress());
                l.add(o, deviceBTAdd);
                o++;
            }
        }


        if (l.contains("18:3B:D2:E1:88:59")) {
            try {
                temp = bD.createRfcommSocketToServiceRecord(ui);
                bs = temp;
                Log.v("Mac Address", "Shouldn't have connected");

                Thread connectionThread = new Thread(new Runnable() {

                    @Override
                    public void run() {


                        // Make a connection to the BluetoothSocket
                        try {
                            // This is a blocking call and will only return on a
                            // successful connection or an exception
                            bs.connect();
                        } catch (IOException e) {
                            //connection to device failed so close the socket
                            Log.v("Mac Address", "Failure :(");
                            failed = 1;
                            try {
                                bs.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (failed == 1) {

                        } else {
                            Log.v("Mac Address", "Success :)?");
                            failed = 0;
                            communicationThread.start();
                        }


                    }
                });

                connectionThread.start();


                Log.v("Mac Address", "Should have connected");


                Log.v("Mac Address", "Slept");
                communicationThread = new Thread(new Runnable() {


                    @Override
                    public void run() {
                        db = UIDatabaseInterface.getDatabase().getReadableDatabase();
                        Cursor  cursor = db.rawQuery("SELECT * FROM Performance",null);
                        int cols = cursor.getColumnCount();
                        try {
                            Log.v("Mac Address", "At Least");
                            if (failed == 0) {
                                String data = "";
                                SubmissionData sb;

                                if (cursor.moveToFirst()) {
                                    while (cursor.isAfterLast() == false) {

                                        boolean isFirst = true;
                                        String row = "";
                                        for (int i = 0; i < cols; i++) {
                                            if(isFirst) {
                                                row = cursor.getString(i);
                                                Log.v("Mac Address", "get string " + cursor.getString(i));
                                                isFirst = false;
                                            }
                                            else {
                                                Log.v("Mac Address", "get string " + cursor.getString(i));
                                                row = row + ("/" + cursor.getString(i));
                                            }

                                        }
                                        Log.v("Glib", "row is: " + row);

                                        row = row + "\n";
                                        data = data + row;
                                        cursor.moveToNext();
                                    }
                                }
                                sb = new SubmissionData(data);

                                Log.v("Mac Address", "Entered");
                               // String s = "Tired, Exhausted";
                                ConfigEntry con = new ConfigEntry("yo", "lol", "hey", "moo");

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                                ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                                ObjectOutput out = null;


                                byte[] yourBytes = null;

                                try {
                                    out = new ObjectOutputStream(bos);


                                    Log.v("Mac Address", "I was here");
                                    out.writeObject(sb);



                                    Log.v("Mac Address", "nullo");

                                    yourBytes = bos.toByteArray();

                                } catch (Exception e) {

                                    Log.e("Mac Address", "error was " + e.toString());
                                }
                                Log.v("Mac Address", Arrays.toString(yourBytes));
                                try {

                                         /* File is not on the disk, test.txt indicates
                                           only the file name to be put into the zip */

                                    ZipOutputStream zos = new ZipOutputStream(baos);
                                    ZipEntry entry = new ZipEntry("test.txt");

                                    //  ObjectOutputStream obs = new ObjectOutputStream(zos);

                                    zos.putNextEntry(entry);

                                    zos.write(yourBytes);
                                    //obs.writeObject();
                                    //obs.close();
                                    zos.closeEntry();

                                    zos.close();

                                        /* use more Entries to add more files
                                      and use closeEntry() to close each file entry */

                                } catch (Exception ioe) {
                                    ioe.printStackTrace();
                                }


                                Log.v("Mac Address", "Highway to Heaven");
                                os = bs.getOutputStream();
                                byte[] ly = baos.toByteArray();
                                String test = new String(ly, "UTF-8");
                                //  Log.v("Mac Address", test);
                                Log.v("Mac Address", Arrays.toString(ly));
                                try {
                                    Log.v("Mac Address", "WRITING CHA BOI");
                                    ObjectOutputStream oout = new ObjectOutputStream(os);
                                    oout.writeObject(ly);
                                    oout.flush();
                                    ControlledEnterDataActivity.status.setTextColor(Color.GREEN);
                                    ControlledEnterDataActivity.status.setText("Success");
                                    //os.write(bytes);
                                    //os.flush();
                                } catch (IOException e) {
                                    ControlledEnterDataActivity.status.setTextColor(Color.RED);
                                    ControlledEnterDataActivity.status.setText("Failure");
                                }

                                //  haha = new PrintStream(haha, true);
                                //   haha.println("LOL");


                            }

                        } catch (IOException e) {
                            //connection to device failed so close the socket
                            ControlledEnterDataActivity.status.setTextColor(Color.RED);
                            ControlledEnterDataActivity.status.setText("Failure");

                        }
                    }
                });
                Log.v("Mac Address", "Writing Starting Soon");


            } catch (Exception e) {
                ControlledEnterDataActivity.status.setTextColor(Color.RED);
                ControlledEnterDataActivity.status.setText("Failure");
            }
        } //if statement ending


    }

}