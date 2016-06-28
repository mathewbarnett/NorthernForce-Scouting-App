package com.DataEntry;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by alex on 4/20/15.
 */
public abstract class DataEntryRow {

    protected String type;
    protected String columnName;
    protected String text;

    public DataEntryRow(String type, String columnName, String text){
        this.type = type;
        this.columnName = columnName;
        this.text = text;
    }

    public abstract View getView(Context c);

    public abstract String getValue();

    public String getType(){
        return this.type;
    }

    public String getColumnName(){
        return this.columnName;
    }

    public String getText() {
        return text;
    }
}
