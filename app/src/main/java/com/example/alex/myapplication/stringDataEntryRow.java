package com.example.alex.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.myapplication.DataEntryRow;

public class stringDataEntryRow extends DataEntryRow {
    TextView textView;
    EditText editText;

    public stringDataEntryRow(String columnName, String text){
        super("String", columnName, text);
    }

    public View getView(Context c){
        textView = new TextView(c);
        textView.setText(this.columnName);

        editText = new EditText(c);
        editText.setHint(columnName);

        LinearLayout view = new LinearLayout(c);
        view.addView(this.textView);
        view.addView(this.editText);

        return view;
    }

    public String getValue(){
        return editText.getText().toString();
    }
}
