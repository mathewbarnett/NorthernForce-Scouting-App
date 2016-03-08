package com.example.alex.myapplication;

/**
 * Created by Oombliocarius on 3/7/16.
 */
public class SubmissionData {
    //Auto
    int teamNum;
    int matchNum;
    boolean aCrossOut; // did they cross the outworks (autonomous)
    boolean aBreachD; // did they breach any defenses (autonomous)
        boolean[] aBreachedDefenses; // array of size 9, representing all defense possibilities, arranged similar to layout (autonomous)
    boolean aScored; // did they score (autonomous)
       boolean[] aScoring; //array of size 2, first is if high, second is if low (autonomous)

    //Tele
    boolean tBreachD; // did they breach any defenses (tele)
        boolean[]tBreachedDefenses; // array of size 9, representing all defense possibilities, arranged similar to layout (tele)



}
