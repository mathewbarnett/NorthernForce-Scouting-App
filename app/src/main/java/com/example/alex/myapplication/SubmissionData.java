package com.example.alex.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oombliocarius on 3/7/16.
 */
public class SubmissionData implements Serializable {
    //Auto


    ArrayList<ArrayList<String>> twoDArrayList = new ArrayList<ArrayList<String>>();

    public SubmissionData() {



    }

    public void setSubmitData(int matchNu, String[] data) {

        twoDArrayList.add(matchNu, new ArrayList<String>(Arrays.asList(data)));




    }



}
