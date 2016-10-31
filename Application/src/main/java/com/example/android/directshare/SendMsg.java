package com.example.android.directshare;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by Xi on 10/30/16.
 */

public class SendMsg extends Service {
    public SendMsg() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("send", "service is called");
        String phoneNumber = intent.getStringExtra("PHONE_NUMBER");
        String msg = intent.getStringExtra("TEXT_MSG");
        Log.i("send", "Send a message to " + phoneNumber + " text: " + msg);
        SmsManager smsManager = SmsManager.getDefault();
        //This API throws IllegalArgumentException if the message body is empty
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);

        return START_STICKY;
    }
}
