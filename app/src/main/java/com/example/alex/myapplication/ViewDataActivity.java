package com.example.alex.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by alex on 3/9/15.
 */
public class ViewDataActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.TeamNumber);
        textView.setText("Data View");
    }


}
