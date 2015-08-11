package com.example.alex.myapplication;

import android.content.Context;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
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
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                boolean hadFocus = false;
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        hadFocus = !hadFocus;
                        return;
                    }
                    if (!hasFocus && hadFocus) {
                        EditText editText = (EditText) v;
                        Log.v("FOO", "Text was " + editText.getText());
                        Log.v("FOO", "ColumnName is " + editText.getHint());
                        for (int i = 0;i<values.length;i++) {
                            if (values[i].getColumnName().equals(editText.getHint())) {
                                Log.v("FOO", "The position you need in values is " + i);
                                values[i].setValue(editText.getText().toString());
                                return;
                            }
                        }
                    }
                }
            });
        }
        if(values[position].getType().equals("int")){
            rowView = inflater.inflate(R.layout.int_entry, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.int_entry_textView);
            textView.setText(values[position].getColumnName());

            EditText editText = (EditText) rowView.findViewById(R.id.int_entry_editText);
            editText.setHint(values[position].getColumnName());
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                boolean hadFocus = false;

                /**
                 *  This code is a little Kluge.
                 *  It uses the editText hint to relocate its data source inside of values
                 *  Then it will be able ot store the value anytime it loses focus
                 * @param v
                 * @param hasFocus
                 */
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        hadFocus = !hadFocus;
                        return;
                    }
                    if (!hasFocus && hadFocus) {
                        EditText editText = (EditText) v;
                        Log.v("FOO", "Text was " + editText.getText());
                        Log.v("FOO", "ColumnName is " + editText.getHint());
                        for (int i = 0;i<values.length;i++) {
                            if (values[i].getColumnName().equals(editText.getHint())) {
                                Log.v("FOO", "The position you need in values is " + i);
                                values[i].setValue(editText.getText().toString());
                                return;
                            }
                        }
                    }
                }
            });
        }
        if(rowView == null){
            Log.e("DataEntryAdapter", "rowView was null at position: " + position);
        }

        return rowView;
    }
}
