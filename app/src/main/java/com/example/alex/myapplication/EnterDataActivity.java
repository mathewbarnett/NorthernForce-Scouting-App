package com.example.alex.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by AlexK on 12/22/2015.
 */
public class EnterDataActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private static UIDatabaseInterface uiDatabaseInterface;
    private View dataEntryViews[];

    private int viewBaseViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_entry_layout);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.dataEntryLinearLayout);
        viewBaseViews = linearLayout.getChildCount();

        uiDatabaseInterface = MainActivity.uiDatabaseInterface;

        DataEntryRow rows[] = uiDatabaseInterface.getDataEntryRows();
        dataEntryViews = new View[rows.length];

        Context c = this.getBaseContext();
        for(int i = 0; i < rows.length; i++){
            dataEntryViews[i] = rows[i].getView(c);
        }
        this.createListView();

        //tables for the spinner
        //ArrayList<String> tables = UIDatabaseInterface.getTableNames();
        ArrayList<String> tables = new ArrayList<>();
        tables.add("Performance");

        tables.remove("android_metadata");

        Spinner tableSpinner = (Spinner) (findViewById(R.id.dataEntryTableSpinner));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_item, tables);
        tableSpinner.setAdapter(spinnerAdapter);
        tableSpinner.setOnItemSelectedListener(this);

    }

    //create the list view
    private void createListView(){

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.dataEntryLinearLayout);
        int currentChildCount = linearLayout.getChildCount();
        linearLayout.removeViewsInLayout(viewBaseViews, currentChildCount - viewBaseViews);

        DataEntryRow rows[] = uiDatabaseInterface.getDataEntryRows();
        Context c = this.getBaseContext();
        for(int i = 0; i < rows.length; i++){
            dataEntryViews[i] = rows[i].getView(c);
        }

        //goes through the rows and adds them to the linear layout
        for(View view :dataEntryViews){
            linearLayout.addView(view);
            Log.v("EnterDataActivity", "added a row to the linear layout");

        }

        //DataEntryAdapter adapter = new DataEntryAdapter(this.getBaseContext(), UIDatabaseInterface.getDataEntryRows());
        //listView.setAdapter(adapter);


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
    //when they select something from the spinner at the top
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedTable = (String) adapterView.getItemAtPosition(i);

        Log.v("EnterDataActivity", "The spinner selected the table " + selectedTable);

        UIDatabaseInterface.setCurrentDataEntryTable(selectedTable);

        UIDatabaseInterface.createDataEntryRows(UIDatabaseInterface.getTableList());

        this.createListView();
    }

    @Override
    //when they don't select something from the spinner at the top
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
