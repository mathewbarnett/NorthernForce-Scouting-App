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

    public UIDatabaseInterface(Context context){
        this.database = new MySQLiteHelper(context);

        ConfigParser configParser = new ConfigParser();
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("configuration_file");
            ArrayList<DatabaseTable> tables = configParser.parse(is);

            this.teamTable = tables.get(0);
            this.matchTable = tables.get(1);

            if(teamTable.getName().equals("Teams")){
                teamTableColumns = tables.get(0).getColumns();
            }
            if(matchTable.getName().equals("Matches")){
                matchTableColumns = tables.get(1).getColumns();
            }

        } catch (XmlPullParserException e) {
            Log.e("UIDatabaseInterface", "XmlPullParserException");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("UIDatabaseInterface", "IOException");
        }

        database.onUpgrade(database.getWritableDatabase(), 0, 1);

        this.doesTeamTableExist = database.doesTableExists(teamTable.getName());
        this.doesMatchTableExist = database.doesTableExists(matchTable.getName());

        this.makeTables();

        listTables();

        listMatchesColumns();

        this.makeUI();
        this.populateDatabase();
    }

    public static void listTables(){
        Cursor tables = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'");
        if(tables.moveToFirst()){
            do{
                Log.v("UIdatabase", "one table is " + tables.getString(0));
            }while(tables.moveToNext());
        }
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
    public static void makeTables(){
        if(!doesTeamTableExist) {
            Log.v("UIdatabase", "Creating Matches table");
            Iterator<ConfigEntry> teamTableIterator = teamTableColumns.iterator();
            database.dropTable(teamTable.getName());

            String createTeamTable = "CREATE TABLE  " + teamTable.getName() + "( _id INTEGER PRIMARY KEY ";

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
            Log.v("UIdatabase", "Creating Teams table");
            Iterator<ConfigEntry> matchTableIterator = matchTableColumns.iterator();

            database.dropTable(matchTable.getName());
            String createMatchTable = "CREATE TABLE " + matchTable.getName() + "( _id INTEGER PRIMARY KEY ";
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
}
