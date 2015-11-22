package com.brickeducation.brickapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin on 2015-11-21.
 */
public class QuestionListenerService extends Service {

    private static final String ACTION="android.provider.Telephony.SMS_RECEIVED";
    public static final String MESSAGE_KEY = "com.brickeducation.brickapp.message_key";
    BrickAppDbHelper helper;

    BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            helper = new BrickAppDbHelper(context);
            Log.i("SMS", "RECIEVED INTENT: " + intent.getAction());

            Bundle smsBundle = intent.getExtras();

            Object[] psudObject = (Object[]) smsBundle.get("pdus");

            if (psudObject!=null){

                SmsMessage message = SmsMessage.createFromPdu((byte[]) psudObject[0]);

                String messageText = message.getDisplayMessageBody();
                String senderText = message.getDisplayOriginatingAddress();

                if ((senderText.equals("+16473601892")||senderText.equals("+12897881489"))&&(messageText.charAt(0)==(';')||messageText.charAt(38)==';')){

                    NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_menu_info_details)
                            .setContentTitle("You have a new test!")
                            .setContentText("Tap to open Flask")
                            .setAutoCancel(true);

                    Question question = new Question(messageText);


                    if (helper.getTestFromName(question.getTestName())!=null){
                        Test testWithName = helper.getTestFromName(question.getTestName());
                        testWithName.updateTestContent(question.getContent());
                        helper.updateTest(testWithName);

                    }else{
                        List<String> questions = new ArrayList<>();
                        questions.add(question.getContent());
                        Test newTest = new Test(questions, question.getTestName());
                        helper.addTest(newTest);
                    }

                    if (BrickAppDbHelper.progressToDouble(question.getTestProgress())==1.0){

                        Intent questionIntent = new Intent(context, BrickActivity.class);
                        questionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);

                        taskStackBuilder.addParentStack(BrickActivity.class);
                        taskStackBuilder.addNextIntent(questionIntent);

                        PendingIntent pi = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                        notBuilder.setContentIntent(pi);

                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        manager.notify(42, notBuilder.build());
                    }

                }

            }

        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        this.registerReceiver(smsReceiver, filter);
    }

    @Override
    public void onDestroy(){
        this.onDestroy();
        this.unregisterReceiver(smsReceiver);
    }
}
