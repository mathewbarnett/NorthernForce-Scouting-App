package com.example.alex.myapplication;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 4/18/15.
 */
public class ConfigParser {

    private static final String ns = null;

    public ArrayList parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            //Log.v("ConfigParser", "returning readFeed");
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private ArrayList readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<DatabaseTable> tables = new ArrayList<DatabaseTable>();

        parser.require(XmlPullParser.START_TAG, ns, "config");
        //Log.v("ConfigParser", "did parser.require");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("TeamTable")) {
                tables.add(new DatabaseTable("TeamTable",readTeamTable(parser, new ArrayList<ConfigEntry>())));
                //Log.v("ConfigParser", "added read team table");
            } else if(name.equals("MatchTable")) {
                tables.add(new DatabaseTable("MatchTable",readTeamTable(parser, new ArrayList<ConfigEntry>())));
                //Log.v("ConfigParser", "added read match table");
            }
            else{
                skip(parser);
            }
        }

        return tables;
    }

    public ArrayList<ConfigEntry> readTeamTable(XmlPullParser parser, ArrayList<ConfigEntry> list) throws XmlPullParserException, IOException{
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            ConfigEntry entry = new ConfigEntry();

            String name = parser.getName();
            entry.setType(name);
            //Log.v("ConfigParser", name);

            String text = readText(parser);
            entry.setText(text);
            //Log.v("ConfigParser", text);
            list.add(entry);
        }

        return list;
    }

    public List<ConfigEntry> readMatchTable(XmlPullParser parser, List<ConfigEntry> list) throws XmlPullParserException, IOException{
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            ConfigEntry entry = new ConfigEntry();

            String name = parser.getName();
            entry.setType(name);
            //Log.v("ConfigParser", name);

            String text = readText(parser);
            entry.setText(text);
            //Log.v("ConfigParser", text);
            list.add(entry);
        }

        return list;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        //Log.v("ConfigParser", "result: " + result);
        return result;
    }

}

