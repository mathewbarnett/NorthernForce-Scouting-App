package com.example.alex.myapplication;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import static com.example.alex.myapplication.MySQLiteHelper.*;

/**
 * Created by alex on 3/9/15.
 */
public class ViewDataActivity extends ActionBarActivity {

    private CursorAdapter dataSource;
    private MySQLiteHelper mySQLiteHelper;
    private UIDatabaseInterface uiDatabaseInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);

        uiDatabaseInterface = MainActivity.uiDatabaseInterface;

        mySQLiteHelper = uiDatabaseInterface.getDatabase();

        uiDatabaseInterface.populateDatabase();

        GridView gridView = (GridView) (findViewById(R.id.dataViewGridView));

        ViewDataAdapter viewDataAdapter = new ViewDataAdapter(mySQLiteHelper, this);

        viewDataAdapter.getItem(0);

        gridView.setAdapter(viewDataAdapter);
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
