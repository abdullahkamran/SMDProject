package com.smdproject.smdproject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSService extends Service {
    public SMSService() {
    }

    @Override
    public void onCreate(){
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

        registerReceiver(receiver,filter);
    }
    @Override
    public void onDestroy(){
        unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    private final BroadcastReceiver receiver=new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent){
            String action=intent.getAction();
            if(action.equals("android.provider.Telephony.SMS_RECEIVED")) {

                Bundle bundle=intent.getExtras();
                try{
                    if(bundle!=null){
                        final Object[] pdusObj=(Object[])bundle.get("pdus");
                        for(int i=0;i<pdusObj.length;i++){

                            SmsMessage current=SmsMessage.createFromPdu((byte[])pdusObj[i]);
                            String phoneNumber=current.getDisplayOriginatingAddress();
                            String msg=current.getDisplayMessageBody();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("SMS Receiver","Exception SMS Receiver"+e);
                }
            }
        }

    }
}
