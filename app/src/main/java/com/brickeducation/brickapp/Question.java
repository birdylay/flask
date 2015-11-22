package com.brickeducation.brickapp;

import android.util.Log;

/**
 * Created by Colin on 2015-11-21.
 */
public class Question {

    private String content;

    public Question(String content){
        if (content.contains("Sent from your Twilio trial account - ")){
            this.content = content.substring(38, content.length()-1);
        }else{
            this.content = content;
        }

    }

    public String getTestName(){
        String name = this.content.split(":")[0];

        return name.substring(1, name.length());
    }

    public String getTestProgress(){
        return this.content.split(":")[1];
    }

    public String getQuestion(){
        Log.i("TAG", content);
        return content.split("\\?")[0].split(":")[2]+"?";
    }

    public String getA(){
        String noQuestion = content.split("\\?")[1];
        return noQuestion.split("A:")[1].split("B:")[0];

    }

    public String getB(){
        String noQuestion = content.split("\\?")[1];
        return noQuestion.split("B:")[1].split("C:")[0];
    }

    public String getC(){
        String noQuestion = content.split("\\?")[1];
        return noQuestion.split("C:")[1].split("D:")[0];

    }

    public String getD(){
        String noQuestion = content.split("\\?")[1];
        return noQuestion.split("D:")[1];

    }

    public String getContent(){
        return content;
    }
}
