package com.example.alex.myapplication;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by alex on 8/11/15.
 */
public class ViewDataAdapter extends BaseAdapter {

    private MySQLiteHelper mySQLiteHelper;
    private int count;
    private ViewDataActivity viewDataActivity;
    private static String currentTable;

    private static String searchedTeam;

    public ViewDataAdapter(MySQLiteHelper mySQLiteHelper, ViewDataActivity viewDataActivity){
        this.currentTable = UIDatabaseInterface.getCurrentDataViewTable();
        this.mySQLiteHelper = mySQLiteHelper;

        Cursor c = mySQLiteHelper.selectFromTable("*", "Performance");

        this.count = c.getCount();

        this.viewDataActivity = viewDataActivity;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(viewDataActivity);
        view.setText("");

        Cursor cursor;
        if(searchedTeam != null){
            cursor = mySQLiteHelper.selectFromTableWhere("*", "Performance", "Team_Number = " + searchedTeam);
            this.count = cursor.getCount();
        }
        else{
           cursor = mySQLiteHelper.selectFromTable("*", "Performance");
            this.count = cursor.getCount();
        }

        if(position >= this.getCount()){
            return new TextView(viewDataActivity);
        }
        cursor.moveToPosition(position);

        String[] fullNames = UIDatabaseInterface.getFullNameForTable("Performance");
        //i = 1 to skip _id column
        for (int i = 1; i < cursor.getColumnCount(); i++) {
            Log.v("VeiwDataAdapter", "the cursor count is " + cursor.getCount() + " and i = " + i);
            Log.v("ViewDataAdapter", "adding following to text view " + fullNames[i - 1] + ": " + cursor.getString(i));
            view.setText(view.getText() + "\n" + fullNames[i - 1] + ": " + cursor.getString(i));
            Log.v("ViewDataAdapter", "view text has text of " + view.getText());
        }

        return view;
    }

    public static String getSearchedTeam() {
        return searchedTeam;
    }

    public static void setSearchedTeam(String team) {
        searchedTeam = team;
    }

}
