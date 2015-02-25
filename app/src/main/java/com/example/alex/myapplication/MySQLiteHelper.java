package com.example.alex.myapplication;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Scouting_Data";
    public static final String ID = "_id";
    public static final String TOTES_STACKED = "Totes_Stacked";
    public static final String CAN_STACK_CONTAINERS = "Can_Stack_Containers";
    public static final String MOVEMENT = "Movement";


    public MySQLiteHelper(Context context) {
        super(context, "Scouting_Data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" +
                ID + " INTEGER PRIMARY KEY" +
                TOTES_STACKED + " INTEGER" +
                CAN_STACK_CONTAINERS + " INTEGER" +
                MOVEMENT + " INTEGER" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addContact(SQLContact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOTES_STACKED, contact.getTotesStacked());
        values.put(CAN_STACK_CONTAINERS, contact.getCanStackContainers());
        values.put(MOVEMENT, contact.getMovement());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    SQLContact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { ID,
                        TOTES_STACKED, CAN_STACK_CONTAINERS, MOVEMENT }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SQLContact contact = new SQLContact(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
        // return contact
        return contact;
    }

}
