package com.brickeducation.brickapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Colin on 2015-11-21.
 */
public class BrickAppDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "brickAppTests.db";
    private static final String BRICK_TEST_TABLE = "brickAppTests";

    //Keys for database
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUESTIONS = "question";



    public static String strSeparator = "__,__";
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }

    public static double progressToDouble(String doubleToParse){
        double num1 = Double.parseDouble(doubleToParse.split("/")[0]);
        double num2 = Double.parseDouble(doubleToParse.split("/")[1]);
        return (num1/num2);
    }




    public BrickAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TEST_TABLE = "CREATE TABLE "+BRICK_TEST_TABLE+"("+KEY_ID+" TEXT PRIMARY KEY,"
                +KEY_NAME+" STRING,"+KEY_QUESTIONS+" STRING)";
        db.execSQL(CREATE_TEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BRICK_TEST_TABLE);
        onCreate(db);
    }

    public void addTest(Test test){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, test.getID());
        values.put(KEY_NAME, test.getTestName());
        values.put(KEY_QUESTIONS, convertArrayToString(test.getTestContentAsArray()));
        db.insert(BRICK_TEST_TABLE, null, values);
        db.close();
    }

    public Test getTestFromName(String name){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+BRICK_TEST_TABLE, null);

        if (cursor.moveToNext()){
            do{
                Test test = new Test(convertStringToArray(cursor.getString(2)), cursor.getString(1));

                if (test.getTestName().equals(name)) {
                    db.close();
                    return test;
                }
            }while (cursor.moveToNext());
        }
        db.close();
        return null;
    }

    public List<Test> getAllTests(){
        List<Test> tests = new ArrayList<>();

        //Select all from database
        String selectQuery = "SELECT * FROM "+BRICK_TEST_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                //Adding a new sleep record to the list that will be returned
                tests.add(new Test(convertStringToArray(cursor.getString(2)), cursor.getString(1)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tests;
    }

    public void updateTest(Test test){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, test.getID());
        values.put(KEY_NAME, test.getTestName());
        values.put(KEY_QUESTIONS, convertArrayToString(test.getTestContentAsArray()));


        db.update(BRICK_TEST_TABLE, values, KEY_NAME + " = ?", new String[]{test.getTestName()});
        db.close();
    }

    public void deleteTest(Test test){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, test.getID());
        values.put(KEY_NAME, test.getTestName());
        values.put(KEY_QUESTIONS, convertArrayToString(test.getTestContentAsArray()));


        int i = db.delete(BRICK_TEST_TABLE, KEY_NAME + " = ?", new String[]{test.getTestName()});
        db.close();
    }



}
