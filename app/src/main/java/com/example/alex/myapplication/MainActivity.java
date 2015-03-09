package com.example.alex.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.provider.DocumentsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    boolean textChanged = false;
    boolean hasDoneTests = false;
    MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MySQLiteHelper(this.getBaseContext());

        Spinner spinner = (Spinner) findViewById(R.id.MovementSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movement_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    public void setText(View view){
//        DocumentParser documentParser = new DocumentParser();
//
//        AssetManager assetManager = getBaseContext().getAssets();
//        InputStream inputStream = null;
//        try {
//            inputStream = assetManager.open("sampleCSV.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String csvFileString = DocumentParser.copyInputStreamToString(inputStream);
//
//        documentParser.consumeDocument(csvFileString);
//
//        TextView text = (TextView) findViewById(R.id.textView);
//        text.setText("consumed document");
//
//        TextView text2 = new TextView(this);
//        text2.setText("These are some words");
    }

    public void runTests(){
        Tests test = new Tests();
        test.testDocumentParserGetLengthReturnsCorrect();
        test.testDocumentParserGetValueReturnsCorrect();
    }

    public void runTests(View view){
        Tests test = new Tests();
        test.testDocumentParserGetLengthReturnsCorrect();
        test.testDocumentParserGetValueReturnsCorrect();
        test.testSQLite(this.getBaseContext());
    }

    public void TeamNumber(View view){
        String tag = (String) (view.getTag());

        TextView textView = (TextView) (findViewById(R.id.TeamNumberNumber));

        if(tag.equals("+")){
            int textViewValue = Integer.parseInt(textView.getText().toString());
            textViewValue++;
            textView.setText(String.valueOf(textViewValue));
        }
        else if(tag.equals("-")){
            int textViewValue = Integer.parseInt(textView.getText().toString());
            textViewValue--;
            textView.setText(String.valueOf(textViewValue));
        }
    }

    public void totesStacked(View view){
        String tag = (String) (view.getTag());

        TextView textView = (TextView) (findViewById(R.id.TotesStackedNumber));

        if(tag.equals("+")){
            int textViewValue = Integer.parseInt(textView.getText().toString());
            textViewValue++;
            textView.setText(String.valueOf(textViewValue));
        }
        else if(tag.equals("-")){
            int textViewValue = Integer.parseInt(textView.getText().toString());
            if(textViewValue == 0){

            }
            else {
                textViewValue--;
                textView.setText(String.valueOf(textViewValue));
            }
        }
    }

    public void addDataToTable(View view){
        SQLContact contact = new SQLContact("0", "0", "0", "0");

        EditText editText =  (EditText) findViewById(R.id.TeamNumberNumber);
        TextView textView = (TextView) findViewById(R.id.TotesStackedNumber);
        CheckBox checkBox = (CheckBox) findViewById(R.id.CanStackContainersValue);
        Spinner spinner = (Spinner) findViewById(R.id.MovementSpinner);

        String stackContainers;
        if(checkBox.isChecked()){
            stackContainers = "yes";
        }
        else{
            stackContainers = "no";
        }
        contact.setTeamNumber(editText.getText().toString());
        contact.setTotesStacked(textView.getText().toString());
        contact.setCanStackContainers(stackContainers);
        contact.setMovement(spinner.getSelectedItem().toString());

        db.addContact(contact);

        String log = "Id: " + contact.getID() + " ,Team Number: " + contact.getTeamNumber() +  " ,Totes Stacked: " + contact.getTotesStacked() + " ,Can stack containers: " + contact.getCanStackContainers() + " ,Movement: " + contact.getMovement();

        // Writing Contacts to log
        Log.v("Saving Data", log);
    }

    public void viewData(View view){
        Intent i = new Intent(this, ViewDataActivity.class);

        startActivity(i);
    }
}
