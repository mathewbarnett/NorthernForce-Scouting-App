package com.example.alex.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import static com.example.alex.myapplication.MySQLiteHelper.*;

/**
 * Created by alex on 3/9/15.
 */
public class ViewDataActivity extends ListActivity {

    private CursorAdapter dataSource;

    private String fields[] =
            {MySQLiteHelper.TEAM_NUMBER, MySQLiteHelper.AVERAGE_SCORE, MySQLiteHelper.ID};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);

        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);

        SQLiteDatabase database = mySQLiteHelper.getWritableDatabase();

        Cursor data = database.query(MySQLiteHelper.TEAM_TABLE, fields, null, null, null, null, null);

        dataSource = new SimpleCursorAdapter(this,
                R.layout.data_view_row, data, fields,
                new int[]{R.id.rowOne, R.id.rowTwo, R.id.rowThree, R.id.rowFour});

        ListView view = this.getListView();
        view.setHeaderDividersEnabled(true);
        view.addHeaderView(getLayoutInflater().inflate(R.layout.data_view_row, null));

        setListAdapter(dataSource);
        setListAdapter(dataSource);

//        TextView textView = (TextView) findViewById(R.id.TeamNumber);
//        textView.setText("Data View");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_enterData){
            Intent i = new Intent(this, MainActivity.class);

            startActivity(i);
        }

        if (id == R.id.action_viewData){

        }

        return super.onOptionsItemSelected(item);
    }
}
