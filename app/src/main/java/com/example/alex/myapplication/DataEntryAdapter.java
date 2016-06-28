package com.example.alex.myapplication;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by alex on 4/20/15.
 */
public class DataEntryAdapter{

    private Context context;
    private DataEntryRow[] values;


    public DataEntryAdapter(Context context, DataEntryRow[] objects, View view) {
        this.context = context;
        this.values = objects;

        for(DataEntryRow row : objects){

        }

    }


    public View getView(DataEntryRow row, View convertView, ViewGroup parent) {

        Log.v("DataEntryAdapter", "values lenght is " + values.length);
        Log.v("DataEntryAdapter", "row is " + row.toString());
        return row.getView(context);

        /*LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;

        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                EditText editText = (EditText) v;
                Log.v("FOO", "Text was " + editText.getText());
                Log.v("FOO", "ColumnName is " + editText.getHint());
                for (int i = 0;i<values.length;i++) {
                    if (values[i].getColumnName().equals(editText.getHint())) {
                        Log.v("FOO", "The position you need in values is " + i);
                        values[i].setValue(editText.getText().toString());
                        return false;
                    }
                }
                return false;
            }
        });
        new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                EnhancedRadioButton yesButton = (EnhancedRadioButton) view.findViewById(R.id.yes_or_no_entry_yesButton);
                for (int i = 0; i < values.length; i++) {
                    if (values[i].getColumnName().equals(yesButton.getColName())) {
                        Log.v("FOO", "The position you need in values is " + i);
                        values[i].setValue(yesButton.getText().toString());

                    }
                }

            }
        })
        if(rowView == null){
            Log.e("DataEntryAdapter", "rowView was null at position: " + position);
        }

        return rowView;*/
    }
}
