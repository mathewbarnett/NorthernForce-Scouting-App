package com.example.alex.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Oombliocarius on 3/6/16.
 */
public class ControlledEnterDataActivity extends ActionBarActivity {

    int l = 0;
    EnhancedRadioButton testo = null;

    String[] boolQuestions = new String[8];


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

            TextView text = (TextView) test.findViewById(R.id.yes_or_no_entry_textView);
            text.setText(boolQuestions[i].toCharArray(), 0, boolQuestions[i].length());
            num++;
        }


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
        Button equals = (Button) findViewById(R.id.bluetoothSync);
        testo.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (view.equals(testo)) {
                    Log.v("Mac Address", "God Bless");

                }
              /*  EnhancedRadioButton ehr = (EnhancedRadioButton) test.findViewById(R.id.yes_or_no_entry_yesButton);
                if (view.equals(ehr)) {
                    Log.v("Mac Address", "Second God Bless");
                }*/
                Log.v("Mac Address", "Shame");
                //        View v = (View) findViewById(R.id.yesOrNo1);
                //        EnhancedRadioButton eh = (EnhancedRadioButton) v.findViewById(R.id.yes_or_no_entry_yesButton);
                //        Log.v("Mac Address", eh.);
                //             EditText test2 = (EditText) findViewById(R.id.comments);
                //            test2.setHeight(300);

//                test2.setVisibility(View.VISIBLE);

            }


        });



    }



}
