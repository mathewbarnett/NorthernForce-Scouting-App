package com.example.alex.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.myapplication.DataEntryRow;

public class stringDataEntryRow extends DataEntryRow {

    Context c;

    TextView textView;
    EditText editText;

    public stringDataEntryRow(String columnName, Context c){
        super("String", columnName);

        this.c = c;

        textView = new TextView(c);
        textView.setText(columnName);

        editText = new EditText(c);
        editText.setHint(columnName);



    }

    public View getView(){
        LinearLayout view = new LinearLayout(c);
        view.addView(this.textView);
        view.addView(this.editText);

        return view;
    }

    public String getValue(){
        return editText.getText().toString();
    }
}
