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

    public static final String ID = "_id";

    public static final String TEAM_TABLE = "Team_Table";
    public static final String TEAM_NUMBER = "Team_Number";
    public static final String AVERAGE_SCORE = "Average_Score";

    public static final String MATCH_TABLE = "Match_Table";
    public static final String MATCH_TEAM_NUMBER = "Team_Number";
    public static final String MATCH_SCORE = "Score";


    private SQLiteDatabase db;

    public MySQLiteHelper(Context context) {
        super(context, "Scouting_Data", null, 1);
        db = this.getWritableDatabase();
    }


    private void createTeamTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE " + TEAM_TABLE +
                " (" +
                ID + " INTEGER PRIMARY KEY," +
                TEAM_NUMBER + " TEXT," +
                AVERAGE_SCORE + " INTEGER" +
                ");");
    }

    private void createMatchTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE " + MATCH_TABLE +
            " (" +
            ID + " INTEGER PRIMARY KEY," +
            MATCH_TEAM_NUMBER + " TEXT," +
            MATCH_SCORE + " INTEGER" +
            ");");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        this.createTeamTable(database);
        this.createMatchTable(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TEAM_TABLE);
        onCreate(db);
    }

    public void createTable(String sqlCommand){
        db.execSQL(sqlCommand);
    }

    public void dropTable(String table){
        db.execSQL("DROP TABLE IF EXISTS " + table);
    }

    public void addValues(String table ,ContentValues values){
        db.insert(table, null, values);
    }

    public Cursor selectFromTable(String table, String column){
        return db.rawQuery("SELECT " + column + " FROM " + table, null);
    }

    public Cursor selectFromTableWhere(String table, String column, String firstArg, String secondArg){
        return db.rawQuery("SELECT " + column + " FROM " + table + " WHERE " + firstArg + " = " + secondArg, null);
    }

    public boolean doesTableExists(String tableName, boolean openDb) {
        if(openDb) {
            if(db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if(!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase();
            }
        }

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void addContact(TeamTable contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEAM_NUMBER, contact.getTeamNumber());
        values.put(AVERAGE_SCORE, contact.getAverageScore());

        db.insert(TEAM_TABLE, null, values);
        db.close();
    }

    public TeamTable getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TEAM_TABLE, new String[] { ID,
                        TEAM_NUMBER, AVERAGE_SCORE }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TeamTable contact = new
                TeamTable(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        return contact;
    }

    public Cursor getTeam(String teamNumber){
        SQLiteDatabase db = this.getReadableDatabase();

        String getTeamQuery = "SELECT * FROM " + TEAM_TABLE + " WHERE " + TEAM_NUMBER + " = " + teamNumber;
        Cursor cursor = db.rawQuery(getTeamQuery, null);

        return cursor;
    }

    public List<TeamTable> getAllContacts() {
        List<TeamTable> contactList = new ArrayList<TeamTable>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TEAM_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.v("Tests", "cursor.get Count: " + String.valueOf(cursor.getCount()));

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TeamTable contact = new TeamTable();
                contact.setID(cursor.getString(0));
                contact.setTeamNumber(cursor.getString(1));
                contact.setAverageScore(cursor.getInt(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public int updateContact(TeamTable contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AVERAGE_SCORE, contact.getAverageScore());

        // updating row
        return db.update(TEAM_TABLE, values, ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    public void deleteContact(TeamTable contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TEAM_TABLE, ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TEAM_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
