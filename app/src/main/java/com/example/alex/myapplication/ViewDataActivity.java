package com.example.alex.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by alex on 3/9/15.
 */
public class ViewDataActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    private CursorAdapter dataSource;
    private MySQLiteHelper mySQLiteHelper;
    private static UIDatabaseInterface uiDatabaseInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);

        mySQLiteHelper = UIDatabaseInterface.getDatabase();

        final ViewDataActivity viewDataActivity = this;
        ArrayList<String> tables = UIDatabaseInterface.getTableNames();

        tables.remove("android_metadata");

        this.createGridView();
    }

    private void createGridView(){
        GridView gridView = (GridView) (findViewById(R.id.dataViewGridView));

        final ViewDataAdapter viewDataAdapter = new ViewDataAdapter(mySQLiteHelper, this);

        gridView.setAdapter(viewDataAdapter);
    }

    public void searchForTeam(View v){
        EditText editText= (EditText) findViewById(R.id.seachedTeam);
        if(editText == null){
            Log.v("Mac Address", "editText is null");
        }
        String text = editText.getText().toString();
        if(text.equals("")){
            Log.v("Mac Address", "setting searched team to null");
            ViewDataAdapter.setSearchedTeam(null);
        }
        else{
            Log.v("Mac Address", "seraching for team " + text);
            ViewDataAdapter.setSearchedTeam(editText.getText().toString());
        }

        this.createGridView();

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedTable = (String) adapterView.getItemAtPosition(i);

        Log.v("EnterDataActivity", "The spinner selected the table " + selectedTable);

        UIDatabaseInterface.setCurrentDataViewTable(selectedTable);

        this.createGridView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
