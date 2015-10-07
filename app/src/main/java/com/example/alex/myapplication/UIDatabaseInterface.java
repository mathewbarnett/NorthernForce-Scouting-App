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
    private ArrayList<ConfigEntry> teamTableColumns;
    private ArrayList<ConfigEntry> matchTableColumns;

    public static MySQLiteHelper database;
    private Context context;

    private boolean doesTeamTableExist;
    private boolean doesMatchTableExist;

    private DataEntryRow[] dataEntryRows;

    private ArrayList<String> teamsInTeamTable;

    public UIDatabaseInterface(Context context){
        this.context = context;
        this.database = new MySQLiteHelper(context);

        ConfigParser configParser = new ConfigParser();
        AssetManager am = this.context.getAssets();
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

        this.doesTeamTableExist = database.doesTableExists(teamTable.getName(), true);
        this.doesMatchTableExist = database.doesTableExists(matchTable.getName(), true);

        this.makeTables();
        this.makeUI();
        this.populateDatabase();
    }

    public void makeTables(){
        if(!doesTeamTableExist) {
            Iterator<ConfigEntry> teamTableIterator = teamTableColumns.iterator();
            database.dropTable(teamTable.getName());
            String createTeamTable = "CREATE TABLE " + teamTable.getName() + "( _id INTEGER PRIMARY KEY ";

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

    public void makeUI() {
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

    public void submitDataEntry(View v) {
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

        this.updateTeamTable();
    }

    public void populateDatabase(){
        for(int i = 0; i<10; i++){
            ContentValues values = new ContentValues();

            values.put("Team_Number", "1");
            values.put("Match_Number", "1");
            values.put("Score", "" + Math.ceil(Math.random()*100));
            values.put("Performance", "5");

            database.addValues(this.matchTable.getName(), values);

            Log.v("UIdatabase", "populated the database and size is: " + database.getTeamTableContactsCount());
        }
        this.updateTeamTable();
    }

    public void updateTeamTable(){
        Cursor teams;
        teams = this.getTeamsNotInTeamTable();

        Log.v("foo", "teams not in team table length is " + teams.getCount());
        if(teams.moveToFirst()){
            do{
                Log.v("foo", "team number is " + teams.getString(0));
                database.addTeamToTeamTable("Team_Number", teams.getString(1));
            }while(teams.moveToNext());
        }

        int count = database.getTeamTableContactsCount();

        Log.v("foo", "count = " + count);
    }

    public int averageScoreForTeam(String teamNumber){
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

    public void viewData(){
        Intent i = new Intent(context, ViewDataActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }

    public Cursor getTeamsNotInTeamTable(){
        Cursor teams = database.selectFromTableWhere("*", "Match_Table", "NOT EXISTS(SELECT NULL FROM TEAM_TABLE WHERE TEAM_TABLE.TEAM_NUMBER = MATCH_TABLE.TEAM_NUMBER)");
        Log.v("foo", "team not in team table length " + teams.getCount());
        return teams;
    }

    public DataEntryRow[] getDataEntryRows(){
        return this.dataEntryRows;
    }

    public MySQLiteHelper getDatabase(){
        return this.database;
    }
}
