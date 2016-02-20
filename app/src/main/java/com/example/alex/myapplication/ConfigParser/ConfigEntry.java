package com.example.alex.myapplication.ConfigParser;

/**
 * Created by alex on 4/18/15.
 */
public class ConfigEntry{

    private String type;
    private String text;

    public ConfigEntry(){

    }

    public ConfigEntry(String type, String text, int table){
        this.type = type;
        this.text = text;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

}
