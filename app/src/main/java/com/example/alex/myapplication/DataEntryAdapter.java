package com.example.alex.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by alex on 4/20/15.
 */
public class DataEntryAdapter extends ArrayAdapter<DataEntryRow> {

    private Context context;
    private DataEntryRow[] values;

    public DataEntryAdapter(Context context, DataEntryRow[] objects) {
        super(context, R.layout.data_entry_layout, objects);
        this.context = context;
        this.values = objects;

        Log.v("DataEntryAdapter", "values length is  " + values.length);

        for(int i = 0; i < values.length; i++) {
            Log.v("DataEntryAdapter", "value at " + i + " is " + values[i].getColumnName());
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;
        if(values[position].getType().equals("String")){
            rowView = inflater.inflate(R.layout.string_entry, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.string_entry_textView);
            textView.setText(values[position].getColumnName());

            EditText editText = (EditText) rowView.findViewById(R.id.string_entry_editText);
            editText.setHint(values[position].getColumnName());
        }
        if(values[position].getType().equals("int")){
            rowView = inflater.inflate(R.layout.int_entry, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.int_entry_textView);
            textView.setText(values[position].getColumnName());

            EditText editText = (EditText) rowView.findViewById(R.id.int_entry_editText);
            editText.setHint(values[position].getColumnName());
        }
        if(rowView == null){
            Log.e("DataEntryAdapter", "rowView was null at position: " + position);
        }
        Log.v("DataEntryAdapter", "position is " + position);
        return rowView;
    }
}
