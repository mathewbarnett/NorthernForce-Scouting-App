package com.example.alex.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Oombliocarius on 3/6/16.
 */
public class ControlledEnterDataActivity extends ActionBarActivity {

    int l = 0;
    EnhancedRadioButton testo = null;

    String[] boolQuestions = new String[8];
    View[] allBoolSets = new View[8];
    View[][] boolOptions = new View[10][2];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_entry_controlled);
        boolQuestions[0] = "Did they cross the Outworks?";
        boolQuestions[1] = "Did they breach a Defense?";
        boolQuestions[2] = "Did they score?";
        boolQuestions[3] = "Did they breach any defenses, which?";
        boolQuestions[4] = "Did they fail to overcome any defenses, which?";
        boolQuestions[5] = "Are they a reliable scorer?";
        boolQuestions[6] = "Did they challenge the tower?";
        boolQuestions[7] = "Did they scale the tower?";
        int num = 1;
        for(int i = 0; i < 8; i++) {
        String id = "yesOrNo" + num;
            int resID = getResources().getIdentifier(id, "id", getPackageName());
            View test = findViewById(resID);
            allBoolSets[i] = test;
            TextView text = (TextView) test.findViewById(R.id.yes_or_no_entry_textView);
            text.setText(boolQuestions[i].toCharArray(), 0, boolQuestions[i].length());
            num++;
        }

        for(int i = 0; i < 8; i++) {

            boolOptions[i][0] = (View) allBoolSets[i].findViewById(R.id.yes_or_no_entry_yesButton);
            boolOptions[i][1] = (View) allBoolSets[i].findViewById(R.id.yes_or_no_entry_noButton);

        }
        View offense = findViewById(R.id.offense);
        View defense = findViewById(R.id.defense);

        final View low = findViewById(R.id.teleLow);
        View high = findViewById(R.id.teleHigh);


        boolOptions[8][0] = high;
        boolOptions[8][1] = low;
        boolOptions[9][0] = offense;
        boolOptions[9][1] = defense;




        View test = findViewById(R.id.yesOrNo1);
        TextView next = (TextView) test.findViewById(R.id.yes_or_no_entry_textView);
        testo = (EnhancedRadioButton) test.findViewById(R.id.yes_or_no_entry_yesButton);


/*
        View test = findViewById(R.id.yesOrNo1);
        TextView next = (TextView) test.findViewById(R.id.yes_or_no_entry_textView);
        testo = (EnhancedRadioButton) test.findViewById(R.id.yes_or_no_entry_yesButton);
        next.setText("Did they cross the Outworks?".toCharArray(), 0, "Did they cross the Outworks?".length());




        View test1 = findViewById(R.id.yesOrNo2);
        TextView next1 = (TextView) test1.findViewById(R.id.yes_or_no_entry_textView);
        next1.setText("Did they breach a Defense?".toCharArray(), 0, "Did they breach a Defense?".length());

        View test2 = findViewById(R.id.yesOrNo3);
        TextView next2 = (TextView) test2.findViewById(R.id.yes_or_no_entry_textView);
        next2.setText("Did they score?".toCharArray(), 0, "Did they score?".length());

        View test3 = findViewById(R.id.yesOrNo4);
        TextView next3 = (TextView) test3.findViewById(R.id.yes_or_no_entry_textView);
        next3.setText("Did they overcome any obstacles, if so which ones?".toCharArray(), 0, "Did they overcome any obstacles, if so which ones?".length());

        View test4 = findViewById(R.id.yesOrNo5);
        TextView next4 = (TextView) test4.findViewById(R.id.yes_or_no_entry_textView);
        next4.setText("Did they fail any attempted obstacles, if so which?".toCharArray(), 0, "Did they fail any attempted obstacles, if so which?".length());

        View test5 = findViewById(R.id.yesOrNo6);
        TextView next5 = (TextView) test5.findViewById(R.id.yes_or_no_entry_textView);
        next5.setText("Are they a reliable scorer?".toCharArray(), 0, "Are they a reliable scorer?".length());

        View test6 = findViewById(R.id.yesOrNo7);
        TextView next6 = (TextView) test6.findViewById(R.id.yes_or_no_entry_textView);
        next6.setText("Did they challenge the tower?".toCharArray(), 0, "Did they challenge the tower?".length());

        View test7 = findViewById(R.id.yesOrNo8);
        TextView next7 = (TextView) test7.findViewById(R.id.yes_or_no_entry_textView);
        next7.setText("Did they scale the tower?".toCharArray(), 0, "Did they scale the tower?".length());
*/

        View.OnClickListener checkToggles = new View.OnClickListener(){
            public void  onClick  (View  v){
                int con = 0;
                int row = 0;
                int col = 0;
                con = findButtonInArray(v);
                row = con/10;
                col = con%10;
                Log.v("Mac Address", "ROW IS: " + row);
                Log.v("Mac Address", "COL IS: " + col);
                if(v instanceof EnhancedRadioButton) {
                    Log.v("Mac Address", "Button");
                    if(col == 0) {
                        EnhancedRadioButton ehr = (EnhancedRadioButton) boolOptions[row][1];
                        if(ehr.isChecked()) {
                            Log.v("Mac Address", "Tried to toggle the " + ehr.getText() + " button");
                            ehr.setChecked(false);
                        }
                    }

                    if(col == 1) {
                        EnhancedRadioButton ehr = (EnhancedRadioButton) boolOptions[row][0];
                        if(ehr.isChecked()) {
                            Log.v("Mac Address", "Tried to toggle the " + ehr.getText() + " button");
                            ehr.setChecked(false);
                        }
                    }
                }
                if(v instanceof CheckBox) {
                    Log.v("Mac Address", "CheckBox");
                    if(col == 0) {
                        CheckBox cb = (CheckBox) boolOptions[row][1];
                        if(cb.isChecked()) {
                            cb.setChecked(false);
                        }
                    }
                    if(col == 1) {
                        CheckBox cb = (CheckBox) boolOptions[row][0];
                        if(cb.isChecked()) {
                            cb.setChecked(false);
                        }
                    }
                }

            }
        };

        for(int i = 0; i < boolOptions.length; i++) {

            for(int l = 0; l < boolOptions[i].length; l++) {
                boolOptions[i][l].setOnClickListener(checkToggles);

            }

        }









        Button equals = (Button) findViewById(R.id.bluetoothSync);




    }

    public int findButtonInArray(View view) {
        for(int i = 0; i < boolOptions.length; i++) {

            for(int l = 0; l < boolOptions[i].length; l++) {

                if(boolOptions[i][l].equals(view)) {
                    return ((i*10) + l);
                }

            }

        }
        return 0;


    }



}
