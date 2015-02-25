package com.example.alex.myapplication;

import android.content.Context;
import android.util.Log;

import junit.framework.Assert;

/**
 * Created by Alex on 2/19/2015.
 */
public class Tests {


    String sampleCSVFile =
            "Name, Data 1, Data 2\n" +
            "column 1, data 1.1, data 1.2\n" +
            "column 2, data 2.1, data 2.2";

    public void testDocumentParserGetLengthReturnsCorrect(){
        DocumentParser documentParser = new DocumentParser();
        documentParser.consumeDocument(sampleCSVFile);

        int docLength = documentParser.getDocLength();
        boolean areEqual = docLength == 3 ;
        if(areEqual){
            Log.v("Tests", "The docLength is correct");
        }
        else if(!areEqual){
            Log.e("Tests", "The docLength is not correct");
        }

    }
    public final void testDocumentParserGetValueReturnsCorrect(){
        DocumentParser documentParser = new DocumentParser();
        documentParser.consumeDocument(sampleCSVFile);

        String docValue = documentParser.getValue(2,"Name");
        boolean areEqual = docValue.equals("column 1");
        if(areEqual){
            Log.v("Tests", "The docValue is correct");
        }
        else if(!areEqual){
            Log.e("Tests", "The docValue is not correct." + "What was returned was (" + docValue + ")");
        }

        docValue = null;
        areEqual = false;
        docValue = documentParser.getValue(3,"Data 2");
        areEqual = docValue.equals("data 2.2");
        if(areEqual){
            Log.v("Tests", "The docValue is correct");
        }
        else if(!areEqual){
            Log.e("Tests", "The docValue is not correct." + "What was returned was (" + docValue + ")");
        }
    }

    public void testSQLite(Context context){
        MySQLiteHelper SQLiteHelper = new MySQLiteHelper(context);

        SQLiteHelper.addContact(new Context(5, 1, 3));

    }
}
