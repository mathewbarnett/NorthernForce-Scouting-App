package com.example.alex.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by alex on 4/18/15.
 */
public class UIDatabaseInterface {

    public static DatabaseTable teamTable;
    public static DatabaseTable matchTable;
    private static ArrayList<ConfigEntry> teamTableColumns;
    private static ArrayList<ConfigEntry> matchTableColumns;

    public static MySQLiteHelper database;

    private static DataEntryRow[] dataEntryRows;

    private static ArrayList<String> teamsInTeamTable;

    private static ArrayList<DatabaseTable> tables;

    public UIDatabaseInterface(Context context){
        this.database = new MySQLiteHelper(context);

        database.onUpgrade(database.getWritableDatabase(), 0, 1);

        ConfigParser configParser = new ConfigParser();
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("configuration_file");
            this.tables = configParser.parse(is);

            for(DatabaseTable table : tables){
                Log.v("UIDatabaseInterface", "Found table " + table.getName() + " to make");

                if(!database.doesTableExists(table.getName())) {
                    database.createTable(table);
                }
            }
        } catch (XmlPullParserException e) {
            Log.e("UIDatabaseInterface", "XmlPullParserException");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("UIDatabaseInterface", "IOException");
        }



        //this.doesTeamTableExist = database.doesTableExists(teamTable.getName());
        //this.doesMatchTableExist = database.doesTableExists(matchTable.getName());

        //this.makeTables();

        listTables();

        listMatchesColumns();

        this.createDataEntryRows(tables, "Performance");
        //this.populateDatabase();
    }

    public static void listTables(){
        for(String tableName : getTableNames()){
            Log.v("UIdatabase", "one table is " + tableName);
        }
    }

    public static ArrayList<String> getTableNames(){
        ArrayList<String> tableList = new ArrayList<String>();

        Cursor tables = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'");
        if(tables.moveToFirst()){
            do{
                tableList.add(tables.getString(0));
            }while(tables.moveToNext());
        }

        return tableList;
    }
    public static void listMatchesColumns(){
        Cursor c = database.selectFromTable("Matches", "*");
        String columns[] = c.getColumnNames();

        int columnCount = c.getColumnCount();
        Log.v("UIdatabase", "Matches column count is " + columnCount);

        for(String columnName : columns){
            Log.v("UIdatabase", "COLUMN IN MATCHES : " + columnName);
        }
    }

    public static void createDataEntryRows(ArrayList<DatabaseTable> tables, String tableName) {
        Cursor performance = database.selectFromTable(tableName, "*");
        int columnCount = performance.getColumnCount();

        //minus one because of id column
        dataEntryRows = new DataEntryRow[columnCount - 1];

        ArrayList<ConfigEntry> columns = null;

        for(DatabaseTable table : tables){
            if(table.getName().equals((tableName))){
                columns = table.getColumns();
            }
        }

        int counter = 0;
        for(ConfigEntry entry : columns){
            String type = entry.getType();
            String columnName = entry.getText();

            DataEntryRow row = new DataEntryRow(type, columnName);
            dataEntryRows[counter] = row;

            counter++;
        }
    }

    public static void submitDataEntry(View v) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (DataEntryRow row : dataEntryRows) {
            if (row.getType().equals("int")) {
                if (row.getValue().equals("")) {
                    Log.v("Interface", "row for column name " + row.getColumnName() + " was empty");
                }
                else {
                    values.put(row.getColumnName(), Integer.parseInt(row.getValue()));
                }
                Log.v("Interface", "type was int, and column name was " + row.getColumnName());
            }
            if (row.getType().equals(("string"))) {
                values.put(row.getColumnName(), row.getValue());
                Log.v("Interface", "type was string");
            }
        }
        database.addValues(matchTable.getName(), values);

        updateTeamTable();
    }

    public static void populateDatabase(){
        for(int i = 0; i<10; i++){
            ContentValues values = new ContentValues();

            values.put("Team_Number", 1);

            values.put("Match_Number", "1");
            values.put("Score", "" + Math.ceil(Math.random()*100));
            values.put("Performance", "5");

            database.addValues(matchTable.getName(), values);

            Log.v("UIdatabase", "populated " + matchTable.getName() + " and size is: " + database.getTeamTableContactsCount());
        }
        updateTeamTable();
    }

    public static void updateTeamTable(){
        Cursor teams;
        teams = getTeamsNotInTeamTable();

        Log.v("foo", "teams not in team table length is " + teams.getCount());
        if(teams.moveToFirst()){
            do{
                Log.v("foo", "added team number " + teams.getString(0));
                ContentValues values = new ContentValues();
                values.put("Team_Number", teams.getString(0));
                database.addValues(teamTable.getName(), values);
            }while(teams.moveToNext());
        }
        averageScoreForTeams();
    }

    public static Cursor getAllTeams(){
        return database.selectFromTable("Matches", "Team_Number");
    }

    public static void averageScoreForTeams(){
        Cursor teams = database.selectFromTable("Teams", "Team_Number");

        int teamCount = teams.getCount();

        if(teamCount == 0){
            return;
        }

        if(teams.moveToFirst()){
            do{
                database.updateCell(teamTable.getName(),
                        "Average_Score", "" + getAverageScoreForTeams(teams.getInt(teams.getColumnIndex("Team_Number"))),
                        "Team_Number = " + teams.getInt(teams.getColumnIndex("Team_Number")));
            }while(teams.moveToNext());
        }
    }

    public static int getAverageScoreForTeams(int teamNumber){
        Cursor matches = database.selectFromTableWhere("Score", "Matches", "Team_Number = " + teamNumber);
        int count = matches.getCount();
        if(count == 0){
            return 0;
        }
        int average = 0;
        if(matches.moveToFirst()){
            do{
                average += matches.getInt(0);
            }while(matches.moveToNext());
        }
        average = average / count;
        return average;
    }


    public static Cursor getTeamsNotInTeamTable(){
        Cursor teams = database.selectFromTableExcept("Team_Number", "Matches", "SELECT Team_Number FROM Teams");
        return teams;
    }

    public static DataEntryRow[] getDataEntryRows(){
        return dataEntryRows;
    }

    public static MySQLiteHelper getDatabase(){
        return database;
    }

    public static ArrayList<DatabaseTable> getTableList(){
        return tables;
    }
}
