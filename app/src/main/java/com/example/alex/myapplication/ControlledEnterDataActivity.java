package com.example.alex.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Oombliocarius on 3/6/16.
 */
public class ControlledEnterDataActivity extends ActionBarActivity {


    BluetoothAdapter bl;
    ControlledEnterDataActivity ceda = this;

    UUID uuid = UUID.fromString("e720951a-a29e-4772-b32e-7c60264d5c9b");
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


                //     Log.v("Mac Address", device.getName());
                if(device.getAddress().equalsIgnoreCase("18:3b:d2:e1:88:59")) {
                    Log.v("Mac Address", device.getName() + "\n" + device.getAddress());
                    Aggro ag = new Aggro(uuid, ceda, device);
                    Thread t = new Thread(ag);
                    t.start();
                    bl.cancelDiscovery();
                    unregisterReceiver(mReceiver);
                }
            }
        }
    };





























    int l = 0;
    EnhancedRadioButton testo = null;

    String[] boolQuestions = new String[8];
    View[] allBoolSets = new View[8];
    View[][] boolOptions = new View[10][2];
    Button blueButton;
    Button submitButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_entry_controlled);
        submitButton = (Button) findViewById(R.id.submit);


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

            boolOptions[i][0] = allBoolSets[i].findViewById(R.id.yes_or_no_entry_yesButton);
            boolOptions[i][1] = allBoolSets[i].findViewById(R.id.yes_or_no_entry_noButton);

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
        final TextView next = (TextView) test.findViewById(R.id.yes_or_no_entry_textView);
        blueButton = (Button) findViewById(R.id.bluetoothSync);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int teamNum = 0;
                int matchNum = 0;
                //YorNs
                String outworks = "no";
                String autoBreachDef = "no";
                String autoScore = "no";
                String teleBreachDef = "no";
                String attemptedDef = "no";
                String reliable = "no";
                String challenged = "no";
                String scaled = "no";
                //checkboxes
                String whereShoot = "no";
                String playStyle = "no";
                //weirdo
                String highOrLowA = "";
                //defense list composite strings
                String whichBreached = "";
                String obstaclesOvercome = "";
                String obstaclesFailed = "";
                //comments
                String comments = "";

                //GET DATA SECTION ************************************************************************
                EditText specifics = null;
                String temp = "";
                EnhancedRadioButton eh = null;
                CheckBox junkCheck = null;

                View defLists = null;

                specifics = (EditText) findViewById(R.id.teamNumber);
                temp = specifics.getText().toString();
                teamNum = Integer.valueOf(temp);


                specifics = (EditText) findViewById(R.id.matchNumber);
                temp = specifics.getText().toString();
                matchNum = Integer.valueOf(temp);

                specifics = (EditText) findViewById(R.id.comments);
                comments = specifics.getText().toString();


                eh = (EnhancedRadioButton) boolOptions[0][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                outworks = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[0][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                outworks = "false";
                }


                eh = (EnhancedRadioButton) boolOptions[1][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    autoBreachDef = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[1][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    autoBreachDef = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[2][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    autoScore = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[2][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    autoScore = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[3][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    teleBreachDef = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[3][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    teleBreachDef = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[4][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    attemptedDef = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[4][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    attemptedDef = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[5][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    reliable = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[5][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    reliable = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[6][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    challenged = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[6][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    challenged = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[7][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    scaled = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[7][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    scaled = "false";
                }
                junkCheck = (CheckBox) boolOptions[8][0];
                if(junkCheck.isChecked()) {
                    whereShoot = "true";
                }
                junkCheck = (CheckBox) boolOptions[8][1];
                if(junkCheck.isChecked()) {
                    whereShoot = "false";
                }

                junkCheck = (CheckBox) boolOptions[9][0];
                if(junkCheck.isChecked()) {
                    playStyle = "true";
                }
                junkCheck = (CheckBox) boolOptions[9][1];
                if(junkCheck.isChecked()) {
                    playStyle = "false";
                }

                //Special checkboxes ( did they score in auto) and defense lists to go


                defLists = (View) findViewById(R.id.defenses1);
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense1);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "lowbar";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense2);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "portcullis";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense3);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "chevaldefrise";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense4);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "moat";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense5);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "ramparts";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense6);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "drawbridge";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense7);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "sallyport";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense8);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "rockwall";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense9);
                if(junkCheck.isChecked()) {
                    whichBreached = whichBreached + " " + "roughterrain";
                }




                defLists = (View) findViewById(R.id.defenses2);
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense1);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "lowbar";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense2);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "portcullis";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense3);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "chevaldefrise";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense4);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "moat";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense5);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "ramparts";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense6);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "drawbridge";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense7);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "sallyport";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense8);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "rockwall";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense9);
                if(junkCheck.isChecked()) {
                    obstaclesOvercome = obstaclesOvercome + " " + "roughterrain";
                }



                defLists = (View) findViewById(R.id.defenses3);
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense1);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "lowbar";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense2);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "portcullis";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense3);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "chevaldefrise";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense4);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "moat";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense5);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "ramparts";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense6);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "drawbridge";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense7);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "sallyport";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense8);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "rockwall";
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense9);
                if(junkCheck.isChecked()) {
                    obstaclesFailed = obstaclesFailed + " " + "roughterrain";
                }



                junkCheck = (CheckBox) findViewById(R.id.autoLow);
                if(junkCheck.isChecked()) {
                    highOrLowA = "low";
                }

                junkCheck = (CheckBox) findViewById(R.id.autoHigh);
                if(junkCheck.isChecked()) {
                    highOrLowA = "high";
                }






                //END GET DATA SECTION ********************************************************************
                //RESET LAYOUT SECTION ********************************************************************
                String bigId = "defenses";
                int bigNumId = 0;

                for(int l = 0; l < 3; l++) {

                    bigId = bigId + (l+1);
                    bigNumId = getResources().getIdentifier(bigId, "id", getPackageName());
                    Log.v("Mac Address", "The big view is: " + bigId);
                    View defOptions = findViewById(bigNumId);

                    CheckBox cb = null;

                        cb = (CheckBox) defOptions.findViewById(R.id.defense1);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense2);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense3);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense4);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense5);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense6);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense7);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense8);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense9);
                        cb.setChecked(false);

                    bigId = "defenses";
                }
                CheckBox cb = null;
                cb = (CheckBox) findViewById(R.id.autoHigh);
                cb.setChecked(false);
                cb = (CheckBox) findViewById(R.id.autoLow);
                cb.setChecked(false);


            for(int i  = 0; i < boolOptions.length; i++) {

            if(boolOptions[i][0] instanceof EnhancedRadioButton) {
               EnhancedRadioButton ehr = (EnhancedRadioButton) boolOptions[i][0];
                ehr.setChecked(false);
                ehr = (EnhancedRadioButton) boolOptions[i][1];
                ehr.setChecked(false);

            }
                if(boolOptions[i][0] instanceof CheckBox) {
                    CheckBox ehr = (CheckBox) boolOptions[i][0];
                    ehr.setChecked(false);
                    ehr = (CheckBox) boolOptions[i][1];
                    ehr.setChecked(false);

                }


            }

                EditText nums = (EditText) findViewById(R.id.matchNumber);

                nums.setText("".toCharArray(), 0, 0);
                nums = (EditText) findViewById(R.id.teamNumber);
                nums.setText("".toCharArray(), 0, 0);
                nums = (EditText) findViewById(R.id.comments);
                nums.setText("".toCharArray(), 0, 0);


                //END OF RESET LAYOUT SECTION ********************************************************************

                //PRINT TIME ******************************************************************************************
                Log.v("Mac Address", String.valueOf(teamNum));
                Log.v("Mac Address", String.valueOf(matchNum));
                Log.v("Mac Address", outworks);
                Log.v("Mac Address", autoBreachDef);
                Log.v("Mac Address", autoScore);
                Log.v("Mac Address", teleBreachDef);
                Log.v("Mac Address", attemptedDef);
                Log.v("Mac Address", reliable);
                Log.v("Mac Address", challenged);
                Log.v("Mac Address", scaled);
                Log.v("Mac Address", whereShoot);
                Log.v("Mac Address", playStyle);
                Log.v("Mac Address", highOrLowA);
                Log.v("Mac Address", whichBreached);
                Log.v("Mac Address", obstaclesOvercome);
                Log.v("Mac Address", obstaclesFailed);
                Log.v("Mac Address", comments);

                ContentValues values = new ContentValues();

                values.put("Team_Number",teamNum);
                values.put("Match_Number",matchNum);
                //values.put("Event_Name",);
                values.put("crossOutW",outworks);
                values.put("breachD",autoBreachDef);
                values.put("whichBreached",whichBreached);
                values.put("didScore",autoScore);
                values.put("highOrLowA",highOrLowA);
                values.put("didOvercome",teleBreachDef);
                values.put("obstaclesOvercome",obstaclesOvercome);
                values.put("failed",attemptedDef);
                values.put("obstaclesFailed",obstaclesFailed);
                values.put("highOrLowT",whereShoot);
                values.put("reliability", reliable);
                values.put("offOrDef",playStyle);
                values.put("didChallenge",challenged);
                values.put("didScale",scaled);
                values.put("Comments",comments);

                UIDatabaseInterface.getDatabase().addValues("Performance", values);


            }
        });


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

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oh = (new BlueConnect().run(ceda, uuid, ceda));
                if(!oh.equals("master")) {
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter);
                    bl = BluetoothAdapter.getDefaultAdapter();

                    bl.startDiscovery();
                }
                else {
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
                    startActivity(discoverableIntent);
                }


            }
        });












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
