package com.example.alex.myapplication;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.*;
import android.content.*;

/**
 * Created by Oombliocarius on 2/19/16.
 */
public class EnhancedRadioGroup extends RadioGroup {


    private String colName = null;
    private Context context;

    protected EnhancedRadioGroup(Context context) {
        super(context);
        this.context = context;
    }




    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}