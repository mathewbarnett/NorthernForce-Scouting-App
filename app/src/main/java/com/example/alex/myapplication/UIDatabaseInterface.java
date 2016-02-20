package com.example.alex.myapplication;

import android.bluetooth.BluetoothAdapter;
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

    private static boolean doesTeamTableExist;
    private static boolean doesMatchTableExist;

    private static DataEntryRow[] dataEntryRows;

    private static ArrayList<String> teamsInTeamTable;

    private boolean hasDatabaseBeenLoaded = false;

    public UIDatabaseInterface(Context context){
       // BluetoothAdapter bL  = BluetoothAdapter.listenUsingRfcommWithServiceRecord();
        this.database = new MySQLiteHelper(context);

        ConfigParser configParser = new ConfigParser();
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("configuration_file");
            ArrayList<DatabaseTable> tables = configParser.parse(is);

            this.teamTable = tables.get(0);
            this.matchTable = tables.get(1);

            if(teamTable.getName().equals("Team_Table")){
                teamTableColumns = tables.get(0).getColumns();

            }
            if(matchTable.getName().equals("Match_Table")){
                matchTableColumns = tables.get(1).getColumns();
            }

        } catch (XmlPullParserException e) {
            Log.e("UIDatabaseInterface", "XmlPullParserException");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("UIDatabaseInterface", "IOException");
        }

        this.doesTeamTableExist = database.doesTableExists(teamTable.getName());
        this.doesMatchTableExist = database.doesTableExists(matchTable.getName());
        Log.w("AHHHHH", "team table name is " + teamTable.getName() + ", does Team Table Exist? " + doesTeamTableExist);

        this.makeTables();
        this.makeUI();
        this.populateDatabase();
    }

    public void makeTables(){
        if(!doesTeamTableExist) {
            Iterator<ConfigEntry> teamTableIterator = teamTableColumns.iterator();
            database.dropTable(teamTable.getName());
            String createTeamTable = "CREATE TABLE IF NOT EXISTS " + teamTable.getName() + "( _id INTEGER PRIMARY KEY ";

            while (teamTableIterator.hasNext()) {
                createTeamTable += ", ";
                ConfigEntry entry = teamTableIterator.next();
                String name = entry.getText();
                String type = "";
                if (entry.getType().equals("String")) {
                    type = "TEXT";
                }
                if (entry.getType().equals("int")) {
                    type = "INTEGER";
                }
                createTeamTable += name + " " + type;
            }

            createTeamTable += ")";

            database.execSQL(createTeamTable);
        }

        if(!doesMatchTableExist) {
            Iterator<ConfigEntry> matchTableIterator = matchTableColumns.iterator();

            database.dropTable(matchTable.getName());
            String createMatchTable = "CREATE TABLE IF NOT EXISTS " + matchTable.getName() + "( _id INTEGER PRIMARY KEY ";
            while (matchTableIterator.hasNext()) {
                createMatchTable += ", ";
                ConfigEntry entry = matchTableIterator.next();
                String name = entry.getText();
                String type = "";
                if (entry.getType().equals("String")) {
                    type = "TEXT";
                }
                if (entry.getType().equals("int")) {
                    type = "INTEGER";
                }
                createMatchTable += name + " " + type;
            }

            createMatchTable += ")";

            database.execSQL(createMatchTable);
        }
    }

    public static void makeUI() {
        dataEntryRows = new DataEntryRow[matchTableColumns.size()];

        Iterator<ConfigEntry> matchIterator = matchTableColumns.iterator();
        int counter = 0;
        while(matchIterator.hasNext()){
            ConfigEntry entry = matchIterator.next();

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
            if(i>5)
                values.put("Team_Number", "1");
            else
                values.put("Team_Number", "2");

            values.put("Match_Number", "1");
            values.put("Score", "" + Math.ceil(Math.random()*100));
            values.put("Performance", "5");

            database.addValues(matchTable.getName(), values);

            Log.v("UIdatabase", "populated the database and size is: " + database.getTeamTableContactsCount());
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
                database.addTeamToTeamTable("Team_Number", teams.getString(0));
            }while(teams.moveToNext());
        }
    }

    public static int averageScoreForTeam(String teamNumber){
        Cursor matches = database.selectFromTableWhere("Score", "MatchTable", "TeamNumber = " + teamNumber);
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
        Cursor teams = database.selectFromTableExcept("Team_Number", "Match_Table", "SELECT Team_Number FROM Team_Table");
        return teams;
    }

    public void printDatabase(DatabaseTable db) {

    ArrayList<ConfigEntry> taken = db.getColumns();

        for(int i = 0; i < taken.size(); i++) {

        Log.v("Mac Address", "-----------------------------");
        Log.v("Mac Address", "TABLE " + i + " TYPE: " + taken.get(i).getType());
        Log.v("Mac Address", "TABLE " + i + " TYPE: " + taken.get(i).getText());
            Log.v("Mac Address", "-----------------------------");


        }

    }


    public static DataEntryRow[] getDataEntryRows(){
        return dataEntryRows;
    }

    public static MySQLiteHelper getDatabase(){
        return database;
    }
}
