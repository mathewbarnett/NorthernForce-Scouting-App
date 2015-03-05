package com.example.alex.myapplication;

/**
 * Created by Alex on 2/24/2015.
 */
public class SQLContact {

    String _id;
    String _team_number;
    String _totes_stacked;
    String _can_stack_containers;
    String _movement;

    public SQLContact(){

    }

    public SQLContact(String id, String team_number, String totes_stacked, String can_stack_containers, String movement){
        this._id = id;
        this._team_number = team_number;
        this._totes_stacked = totes_stacked;
        this._can_stack_containers = can_stack_containers;
        this._movement = movement;
    }

    // constructor
    public SQLContact(String team_number, String totes_stacked, String can_stack_containers, String movement){
        this._team_number = team_number;
        this._totes_stacked = totes_stacked;
        this._can_stack_containers = can_stack_containers;
        this._movement = movement;
    }

    public String getID(){
        return this._id;
    }


    public void setID(String id){
        this._id = id;
    }

    public String getTeamNumber(){
        return this._team_number;
    }

    public void setTeamNumber(String teamNumber){
        this._team_number = teamNumber;
    }

    public String getTotesStacked(){
        return this._totes_stacked;
    }


    public void setTotesStacked(String totesStacked){
        this._totes_stacked = totesStacked;
    }


    public String getCanStackContainers(){
        return this._can_stack_containers;
    }


    public void setCanStackContainers(String canStackContainers){
        this._can_stack_containers = canStackContainers;
    }

    public String getMovement(){
        return this._movement;
    }

    public void setMovement(String movement){
        this._movement = movement;
    }
}
