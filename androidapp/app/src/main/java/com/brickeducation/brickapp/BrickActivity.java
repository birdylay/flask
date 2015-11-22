package com.brickeducation.brickapp;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BrickActivity extends AppCompatActivity {

    BrickAppDbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_brick);

        CardView myTestCard = (CardView) findViewById(R.id.test_card_clickable);
        CardView questionCard = (CardView) findViewById(R.id.questions_card);
        CardView reminderCard = (CardView) findViewById(R.id.reminder_card);
        CardView dateCard = (CardView) findViewById(R.id.date_card);
        CardView accountCard = (CardView) findViewById(R.id.account_card);
        CardView settingsCard = (CardView) findViewById(R.id.settings_card);


        myTestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "TAPPED");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.add(android.R.id.content, TestListFragment.newInstance(), null);
                ft.addToBackStack("1");
                ft.commit();
            }
        });
        Intent intent = getIntent();

        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.add(android.R.id.content, SettingsFragment.newInstance(), null);
                ft.addToBackStack("1");
                ft.commit();
            }
        });

        reminderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.add(android.R.id.content, ReminderFragment.newInstance(), null);
                ft.addToBackStack("1");
                ft.commit();
            }
        });

        questionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    ft.add(android.R.id.content, QuestionCardFragment.newInstance(), null);
                    ft.addToBackStack("1");
                    ft.commit();
            }
        });


        Intent serviceIntent = new Intent(this, QuestionListenerService.class);
        startService(serviceIntent);


    }


    public void resetCount(){
        helper = new BrickAppDbHelper(this);
        CardView myTestCard = (CardView) findViewById(R.id.test_card_clickable);
        List<Test> tests = helper.getAllTests();
        int count = 0;
        for (int i = 0; i<tests.size();i++){
            List<String> strings = new ArrayList<>();
            strings = tests.get(i).getTestContent();
            if (BrickAppDbHelper.progressToDouble(new Question(strings.get(strings.size()-1)).getTestProgress())==1)  count++;
            Log.i("Brick Activity", strings.toString());
        }

        TextView myTests = (TextView) findViewById(R.id.myTests);
        if (count>0){
            myTests.setText("My Tests ("+count+")");
        }else{
            myTests.setText("My Tests");
            myTestCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        resetCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_brick, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
