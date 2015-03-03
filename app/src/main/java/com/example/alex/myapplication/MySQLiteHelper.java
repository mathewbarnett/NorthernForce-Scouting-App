package com.example.alex.myapplication;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;

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
                ID + " INTEGER PRIMARY KEY," +
                TOTES_STACKED + " INTEGER," +
                CAN_STACK_CONTAINERS + " INTEGER," +
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

    public List<SQLContact> getAllContacts() {
        List<SQLContact> contactList = new ArrayList<SQLContact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SQLContact contact = new SQLContact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setTotesStacked(Integer.parseInt(cursor.getString(1)));
                contact.setCanStackContainers(Integer.parseInt(cursor.getString(2)));
                contact.setMovement(Integer.parseInt(cursor.getString(3)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public int updateContact(SQLContact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOTES_STACKED, contact.getTotesStacked());
        values.put(CAN_STACK_CONTAINERS, contact.getCanStackContainers());
        values.put(MOVEMENT, contact.getMovement());

        // updating row
        return db.update(TABLE_NAME, values, ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    public void deleteContact(SQLContact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
