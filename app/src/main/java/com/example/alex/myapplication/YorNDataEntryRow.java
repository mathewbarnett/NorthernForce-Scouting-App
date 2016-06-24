package com.example.alex.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.myapplication.DataEntryRow;

public class YorNDataEntryRow extends DataEntryRow {

    Context c;

    TextView textView;
    RadioGroup radioGroup;
    RadioButton yesButton;
    RadtioButton noButton;

    public YorNDataEntryRow(String columnName, Context c){
        super("String", columnName);

        this.c = c;

        textView = new TextView(c);
        textView.setText(columnName);

        radioGroup = new RadioGroup(c);

        yesButton = new RadioButton(c);
        noButton = new RadioButton(c);

        radioGroup.laddView(yesButton);
        radioGroup.addView(noButton);

    }

    public View getView(){
        LinearLayout view = new LinearLayout(c);
        view.addView(this.textView);
        view.addView(this.radioGroup);

        return view;
    }

    public String getValue(){
        if(yestButton.isChecked()){
            return "yes";
        }
        if(noButton.isChecked()){
            return "no";
        }
    }
}
