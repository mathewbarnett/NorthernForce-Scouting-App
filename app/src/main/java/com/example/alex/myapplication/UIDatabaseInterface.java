package com.example.alex.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by alex on 4/18/15.
 */
public class UIDatabaseInterface {

    private DatabaseTable teamTable;
    private DatabaseTable matchTable;
    private ArrayList<ConfigEntry> teamTableColumns;
    private ArrayList<ConfigEntry> matchTableColumns;

    private MySQLiteHelper database;
    private Context context;

    private boolean doesTeamTableExist;
    private boolean doesMatchTableExist;

    private DataEntryRow[] dataEntryRows;

    public UIDatabaseInterface(Context context){
        this.context = context;
        this.database = new MySQLiteHelper(context);

        ConfigParser configParser = new ConfigParser();
        AssetManager am = this.context.getAssets();
        try {
            InputStream is = am.open("configuration_file");
            //Log.v("Tests", "opened config file");
            ArrayList<DatabaseTable> tables = configParser.parse(is);

            teamTable = tables.get(0);
            matchTable = tables.get(1);

            if(teamTable.getName().equals("TeamTable")){
                teamTableColumns = tables.get(0).getColumns();
            }
            if(matchTable.getName().equals("MatchTable")){
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

            database.createTable(createTeamTable);
        }

        Log.v("Interface", "does match table exist = " + doesMatchTableExist);

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

            Log.v("Interface", "Creating table " + createMatchTable);

            database.createTable(createMatchTable);
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

    public void submitDataEntry(View v){
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        for(DataEntryRow row : dataEntryRows){
            if(row.getType().equals("int")){
                if(row.getValue().equals("")){
                    Log.v("Interface", "row for column name " + row.getColumnName() + " was empty");
                }
                else {
                    values.put(row.getColumnName(), Integer.parseInt(row.getValue()));
                }
                Log.v("Interface", "type was int, and column name was " + row.getColumnName());
            }
            if(row.getType().equals(("string"))){
                values.put(row.getColumnName(), row.getValue());
                Log.v("Interface", "type was string");
            }

        }

        db.insert(matchTable.getName(), null, values);
    }

    public DataEntryRow[] getDataEntryRows(){
        return this.dataEntryRows;
    }
}
