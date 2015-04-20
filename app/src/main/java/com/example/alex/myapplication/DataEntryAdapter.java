package com.example.alex.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

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
        return rowView;
    }
}
