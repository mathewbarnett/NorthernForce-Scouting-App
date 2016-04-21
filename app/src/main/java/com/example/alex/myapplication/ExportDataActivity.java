package com.example.alex.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Oombliocarius on 3/12/16.
 */
public class ExportDataActivity extends ActionBarActivity {

    SQLiteDatabase db;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getBaseContext();

        setContentView(R.layout.export_data);

        Button export = (Button) findViewById(R.id.exportButton);



        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText ed = (EditText) findViewById(R.id.fileName);
                if(ed.getText().toString().equals("")) {
                    ed.setHintTextColor(Color.RED);
                    ed.setHint("Enter File Name");
                }
                else {
                    String entered = ed.getText().toString();
                    backupDatabaseCSV(entered + ".csv");
                 //   readFile(entered + "magio.csv");
                }
            }
        });

    }









    private void readFile(String outFileName) {

        File root = android.os.Environment.getExternalStorageDirectory();
        Log.v("Mac Address", root.getAbsolutePath());

        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, outFileName);
       // dir.mkdirs();
            Log.v("Mac Address", file.getAbsolutePath());

       root = context.getExternalFilesDir("magic");
        Log.v("Mac Address", root.getAbsolutePath());

        try {
            FileReader fileWriter = new FileReader(file);
            BufferedReader out = new BufferedReader(fileWriter);
            String line = out.readLine();
           while( line != null) {
      //         Log.v("Mac Address", line);
               line = out.readLine();
           }
        //    file.delete();
        }
        catch (Exception e) {

        }

    }








    private Boolean backupDatabaseCSV(String outFileName) {



        File root = android.os.Environment.getExternalStorageDirectory();
        ArrayList<String> allRows = new ArrayList<String>();
        ArrayList<Integer> rowIndexes = new ArrayList<Integer>();


        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        dir.mkdirs();
        File file = new File(dir, outFileName);


 //       String filePath = context.getFilesDir().getPath().toString() + "/" + outFileName;
        db = UIDatabaseInterface.getDatabase().getReadableDatabase();
        Cursor  cursor = db.rawQuery("SELECT * FROM Performance",null);
        String[] colNames =  cursor.getColumnNames();
        int cols = cursor.getColumnCount();
        Log.v("Mac Address", "backupDatabaseCSV");
        boolean returnCode = false;
        int i = 0;
        String csvHeader = "";
        String csvValues = "";
        for (i = 0; i < colNames.length; i++) {
            if (csvHeader.length() > 0) {
                csvHeader += ",";
            }
            csvHeader += "\"" + colNames[i] + "\"";
        }

        csvHeader += "\n";
        Log.v("Mac Address", "header=" + csvHeader);

        try {
         //   File outFile = new File(filePath);

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);

            if (cursor != null) {
                Log.v("Mac Address", "Made it past null");
                out.write(csvHeader);
                cursor.moveToFirst();
                int io = 0;
                while (!cursor.isAfterLast()) {
                    csvValues = "";

                    csvValues += Integer.toString(cursor.getInt(0)) + ",";
                    csvValues += Integer.toString(cursor.getInt(1))
                            + ",\"";
                    csvValues += ( cursor.getString(2))     + "\",";
                    csvValues += "\"" + cursor.getString(3) + "\",";

                    csvValues += "\"" + cursor.getString(4) + "\",";

                    csvValues += "\"" + cursor.getString(5) + "\",";

                    csvValues += "\"" + cursor.getString(6) + "\",";

                    csvValues += "\"" + cursor.getString(7) + "\",";

                    csvValues += "\"" + cursor.getString(8) + "\",";

                    csvValues += "\"" + cursor.getString(9) + "\",";

                    csvValues += "\"" + cursor.getString(10) + "\",";

                    csvValues += "\"" + cursor.getString(11) + "\",";

                    csvValues += "\"" + cursor.getString(12) + "\",";

                    csvValues += "\"" + cursor.getString(13) + "\",";

                    csvValues += "\"" + cursor.getString(14) + "\",";

                    csvValues += "\"" + cursor.getString(15) + "\",";

                    csvValues += "\"" + cursor.getString(16) + "\",";
                    if(cursor.getString(17) != null) {
                        csvValues += "\"" + cursor.getString(17).replaceAll("\n", " ") + "\"" + "\n";
                    }
                    else {
                        csvValues += "\"" + "\"" + "\n";
                    }


           //         Log.v("Mac Address", "TEST COL: " + cursor.getString(17));
                    allRows.add(io, csvValues);
                    rowIndexes.add(io, cursor.getInt(1));

                 //   Log.v("Mac Address", "CSV: " + csvValues);





                 /*   csvValues += (cursor.getString(4))
                            + ",";
                    csvValues += Double.toString(cursor.getDouble(5))
                            + ",";
                    csvValues += "\"" + cursor.getString(6) + "\",";
                    csvValues += Double.toString(cursor.getDouble(7))
                            + ",";
                    csvValues += Double.toString(cursor.getDouble(8))
                            + ",";
                    csvValues += Double.toString(cursor.getDouble(9))
                            + "\n";*/
                    cursor.moveToNext();
                    io++;

                }
                allRows.trimToSize();
                rowIndexes.trimToSize();
                String[] y = allRows.toArray(new String[0]);
                int[] x = convertIntegers(rowIndexes);
                Log.v("Mac Address", "ROWS: " + Arrays.toString(y));
                Log.v("Mac Address", "INDEXES: " + Arrays.toString(x));
                Arrays.sort(x);
                int holder = 0;
                int magic = -1;
                    for(int ol = 0; ol < x.length; ol++) {

                        holder = x[ol];

                        int magic1 = -1;
                        for(int lo = 0; lo < y.length; lo++) {

                            String part = y[lo].substring(y[lo].indexOf(",")+1, y[lo].length());
                            String part1 = part.substring(0, part.indexOf(","));

                            if(part1.equals(Integer.toString(holder))) {

                                magic1 = lo;
                                break;

                            }

                        }

                        String temp = y[ol];
                        y[ol] = y[magic1];
                        y[magic1] = temp;




                    }

                for(int m = 0; m < y.length; m++) {
                    out.write(y[m]);

                }


                cursor.close();

            }
            out.close();
            returnCode = true;
        } catch (IOException e) {
            returnCode = false;
            Log.v("Mac Address", "IOException: " + e.getMessage());
        }
     //   dbAdapter.close();
        return returnCode;
    }

    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }













}