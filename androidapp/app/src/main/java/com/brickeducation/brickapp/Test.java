package com.brickeducation.brickapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Colin on 2015-11-21.
 */
public class Test {

    private List<String> testContent;
    private String testName;
    private UUID id;

    public Test(List<String> content, String name){
        testContent = content;
        testName = name;
        id = UUID.randomUUID();
    }

    public Test(String[] content, String name){
        testContent = new ArrayList<>();
        testContent.addAll(Arrays.asList(content));
        testName = name;
        id = UUID.randomUUID();
    }

    public String getID(){
        return id.toString();
    }

    public String getTestName(){
        return testName;
    }

    public List<String> getTestContent(){
        return testContent;
    }

    public void updateTestContent(String questionToAdd){
        Log.i("Test", "BEFORE STRINGS :"+testContent.toString());
        testContent.add(questionToAdd);
        Log.i("Test", "AFTER STRINGS :" + testContent.toString());
    }

    public String[] getTestContentAsArray(){

        String[] stockArr = new String[testContent.size()];
        return testContent.toArray(stockArr);
    }




}
