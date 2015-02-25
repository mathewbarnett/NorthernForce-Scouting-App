package com.example.alex.myapplication;

/**
 * Created by Alex on 2/24/2015.
 */
public class SQLContact {

    int _id;
    int _totes_stacked;
    int _can_stack_containers;
    int _movement;

    public SQLContact(){

    }

    public SQLContact(int id, int totes_stacked, int can_stack_containers, int movement){
        this._id = id;
        this._totes_stacked = totes_stacked;
        this._can_stack_containers = can_stack_containers;
        this._movement = movement;
    }

    // constructor
    public SQLContact(int totes_stacked, int can_stack_containers, int movement){
        this._totes_stacked = totes_stacked;
        this._can_stack_containers = can_stack_containers;
        this._movement = movement;
    }

    public int getID(){
        return this._id;
    }


    public void setID(int id){
        this._id = id;
    }


    public int getTotesStacked(){
        return this._totes_stacked;
    }


    public void setTotesStacked(int totesStacked){
        this._totes_stacked = totesStacked;
    }


    public int getCanStackContainers(){
        return this._can_stack_containers;
    }


    public void setCanStackContainers(int canStackContainers){
        this._can_stack_containers = canStackContainers;
    }

    public int getMovement(){
        return this._movement;
    }

    public void setMovement(int movement){
        this._movement = movement;
    }
}
