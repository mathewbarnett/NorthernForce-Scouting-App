package com.example.alex.myapplication.DataEntry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.alex.myapplication.DataEntry.DataEntryAdapter;
import com.example.alex.myapplication.MainActivity;
import com.example.alex.myapplication.NestedListView;
import com.example.alex.myapplication.R;
import com.example.alex.myapplication.UIDatabaseInterface;

import java.util.ArrayList;

/**
 * Created by AlexK on 12/22/2015.
 */
public class EnterDataActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private static UIDatabaseInterface uiDatabaseInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_entry_layout);

        this.uiDatabaseInterface = MainActivity.uiDatabaseInterface;

        this.createListView();

        ArrayList<String> tables = uiDatabaseInterface.getTableNames();

        tables.remove("android_metadata");

        Spinner tableSpinner = (Spinner) (findViewById(R.id.dataEntryTableSpinner));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_item, tables);
        tableSpinner.setAdapter(spinnerAdapter);
        tableSpinner.setOnItemSelectedListener(this);

    }

    private void createListView(){
        NestedListView listView = (NestedListView) (findViewById(R.id.dataEntryListView));

        DataEntryAdapter adapter = new DataEntryAdapter(this.getBaseContext(), uiDatabaseInterface.getDataEntryRows());
        listView.setAdapter(adapter);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("EnterDataActivity", "Submit was pressed");
                UIDatabaseInterface.submitDataEntry(v);
            }
        });
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

        uiDatabaseInterface.setCurrentDataEntryTable(selectedTable);

        uiDatabaseInterface.createDataEntryRows(uiDatabaseInterface.getTableList());

        this.createListView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
