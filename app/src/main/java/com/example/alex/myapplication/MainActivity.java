package com.example.alex.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;

import com.example.alex.myapplication.DataEntry.EnterDataActivity;
import com.example.alex.myapplication.DataView.ViewDataActivity;

import com.example.alex.myapplication.TheBlueAlliance.*;
import com.example.alex.myapplication.TheBlueAlliance.event.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    boolean textChanged = false;
    boolean hasDoneTests = false;
    private MySQLiteHelper db;
    public static UIDatabaseInterface uiDatabaseInterface;
    boolean isStart = true;
    private Context baseContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.baseContext = this.getBaseContext();

        //this.runTests();

        setContentView(R.layout.activity_main);

        uiDatabaseInterface = new UIDatabaseInterface(this.getBaseContext());

        Button enterData = (Button) (findViewById(R.id.titleScreenEnterData));
        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, EnterDataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });

        Button viewData = (Button) (findViewById(R.id.titleScreenViewData));
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, ViewDataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });

        new BlueAllienceTest().execute(this);
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

        if (id == R.id.action_enterData){

        }

        if (id == R.id.action_viewData){
            Context context = this.getBaseContext();
            Intent i = new Intent(context, ViewDataActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void runTests(){
        Tests test = new Tests();
        test.testDocumentParserGetLengthReturnsCorrect();
        test.testDocumentParserGetValueReturnsCorrect();
        test.testSQLite(this.getBaseContext());
        test.testConfigParser(this.getBaseContext());
    }

    public UIDatabaseInterface getUiDatabaseInterface(){
        return this.uiDatabaseInterface;
    }
}
