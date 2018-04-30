package com.smdproject.smdproject;

import android.app.NotificationChannel;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver {
    private final String DEBUG_TAG = getClass().getSimpleName().toString();
    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private Context mContext;
    private Intent mIntent;

    // Retrieve SMS
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;

        String action = intent.getAction();

        if(action.equals(ACTION_SMS_RECEIVED)){

            String address, str = "";

            SmsMessage[] msgs = getMessagesFromIntent(mIntent);
            if (msgs != null) {
                for (int i = 0; i < msgs.length; i++) {
                    address = msgs[i].getOriginatingAddress();
                    str += msgs[i].getMessageBody().toString();
                }
            }

            if(str.startsWith("@squadApp")){

                str=str.substring(9);
                Toast.makeText(context,"SquadApp recieved the message : "+str,Toast.LENGTH_LONG).show();

                String[] splits=str.split("\n",2);

                String header=splits[0].substring(1);

                String message=splits[1];

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("SquadApp Message")
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);



                abortBroadcast();
            }
            else return;


            // ---send a broadcast intent to update the SMS received in the
            // activity---
            //Intent broadcastIntent = new Intent();
            //broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            //broadcastIntent.putExtra("sms", str);
            //context.sendBroadcast(broadcastIntent);
        }

    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }

    /**
     * The notification is the icon and associated expanded entry in the status
     * bar.
     */
    protected void showNotification(String message) {
        //Display notification...
    }
}