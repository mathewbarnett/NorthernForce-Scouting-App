package com.example.alex.myapplication;

/**
 * Created by alex on 4/20/15.
 */
public class DataEntryRow {

    private String type;
    private String columnName;

    public DataEntryRow(String type, String columnName){
        this.type = type;
        this.columnName = columnName;
    }

    public String getType(){
        return this.type;
    }

    public String getColumnName(){
        return this.columnName;
    }
}
