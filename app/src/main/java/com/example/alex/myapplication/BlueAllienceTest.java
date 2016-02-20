package com.example.alex.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.example.alex.myapplication.TheBlueAlliance.BlueAlliance;
import com.example.alex.myapplication.TheBlueAlliance.event.SimpleEvent;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexK on 2/20/2016.
 */
public class BlueAllienceTest  extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {
        BlueAlliance blueAlliance = new BlueAlliance();
        if(blueAlliance == null){
            Log.v("blueAlliance", "blueAlliance is null");
        }

        try {
            ArrayList<SimpleEvent> events = blueAlliance.getEventList("2016");
            if(events == null){
                Log.v("blueAlliance", "events is null");
            }
            Log.v("blueAlliance", "found " + events.size() + " events");
            for(SimpleEvent e : events){
                Log.v("blueAlliance", "Found a 2016 event: " + e.getName());
            }

            UIDatabaseInterface.populateEventsFromBlueAlliance(events);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
