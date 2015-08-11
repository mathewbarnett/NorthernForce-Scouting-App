package com.example.alex.myapplication;

/**
 * Created by alex on 4/17/15.
 */
public class MatchTable {

    String _id;
    String _teamNumber;
    int _score;

    public MatchTable(){
        
    }

    public MatchTable(String teamNumber, int score){
        this._teamNumber = teamNumber;
        this._score = score;
    }

    public MatchTable(String id, String teamNumber, int score){
        this._id = id;
        this._teamNumber = teamNumber;
        this._score = score;
    }

    public String getid(){
        return this._id;
    }

    public void setid(String id){
        this._id = id;
    }

    public String getTeamNumber(){
        return this._teamNumber;
    }

    public void setTeamNumberi(String teamNumber){
        this._teamNumber = teamNumber;
    }

    public int getScore(){
        return this._score;
    }

    public void setScore(int score){
        this._score = score;
    }
}
