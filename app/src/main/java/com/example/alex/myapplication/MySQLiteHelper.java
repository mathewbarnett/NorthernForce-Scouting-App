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

    public static final String MATCH_TABLE = "Match_Table";

    private SQLiteDatabase db;

    public MySQLiteHelper(Context context) {
        super(context, "Scouting_Data", null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TEAM_TABLE);
    }

    public void execSQL(String sqlCommand){
        db.execSQL(sqlCommand);
    }

    public void dropTable(String table){
        db.execSQL("DROP TABLE IF EXISTS " + table);
    }

    public void addValues(String table ,ContentValues values){
        synchronized (db) {
            db.insert(table, null, values);
        }
    }

    public Cursor selectFromTable(String table, String column){
        return db.rawQuery("SELECT " + column + " FROM " + table, null);
    }

    public Cursor selectFromTableWhere(String column, String table, String condition){
        return db.rawQuery("SELECT " + column + " FROM " + table + " WHERE " + condition, null);
    }

    public Cursor selectFromTableExcept(String column, String table, String condition){
        return db.rawQuery("SELECT " + column + " FROM " + table + " EXCEPT " + condition, null);
    }

    boolean doesTableExists(String tableName){
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE name = '" + tableName + "' and type='table'" , null);
        Log.v("AAAHHH", "does " + tableName + " exists? cursor length is " + cursor.getCount());
        if(cursor.getCount() == 1){
            return true;
        }
        return false;
    }

    public void deleteTeamTableContact(TeamTable contact) {
        db.delete(TEAM_TABLE, ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    public int getTeamTableContactsCount() {
        String countQuery = "SELECT  * FROM " + TEAM_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getMatchTableContactsCount(){
        String countQuery = "SELECT  * FROM " + MATCH_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();

        return count;
    }

    public Cursor getAllMatchTableRows(){
        String selectQuery = "SELECT  * FROM " + MATCH_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public Cursor getAllTeamTableRows(){
        String selectQuery = "SELECT  * FROM " + TEAM_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public void addTeamToTeamTable(String teamNumberColumnName, String teamNumber){
        ContentValues values = new ContentValues();
        values.put(teamNumberColumnName, teamNumber);
        db.insert(TEAM_TABLE, null, values);
    }

}
