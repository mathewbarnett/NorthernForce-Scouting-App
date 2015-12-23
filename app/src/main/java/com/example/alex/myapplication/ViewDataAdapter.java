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
    private String currentTable;

    public ViewDataAdapter(MySQLiteHelper mySQLiteHelper, ViewDataActivity viewDataActivity, String tableName){
        this.currentTable = tableName;
        this.mySQLiteHelper = mySQLiteHelper;

        Cursor c = mySQLiteHelper.selectFromTable(currentTable, "*");

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

        Cursor cursor = mySQLiteHelper.selectFromTable(currentTable, "*");
        cursor.moveToPosition(position);
        //i = 1 to skip _id column
        for (int i = 1; i < cursor.getColumnCount(); i++) {
            //Log.i("ViewDataAdapter", "adding following to text view " + cursor.getColumnName(i) + ": " + cursor.getString(i));
            view.setText(view.getText() + "\n" + cursor.getColumnName(i) + ": " + cursor.getString(i));
        }

        return view;
    }
}
