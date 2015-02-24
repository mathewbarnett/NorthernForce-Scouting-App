package com.example.alex.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public MySQLiteHelper(Context context) {
        super(context, "Scouting_Data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + "Scouting_Data" +
                " (" +
                "_id" + " INTEGER PRIMARY KEY" +
                "Totes_Stacked" + " INTEGER" +
                "Can_Stack_Bins" + " INTEGER" +
                "Movement" + " INTEGER" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + "Scouting_Data ");
        onCreate(db);
    }

}
