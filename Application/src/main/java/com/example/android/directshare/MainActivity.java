/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.directshare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

/**
 * Provides the landing screen of this sample. There is nothing particularly interesting here. All
 * the codes related to the Direct Share feature are in {@link SampleChooserTargetService}.
 */
public class MainActivity extends Activity {

    private EditText mEditBody;

    final static int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      //  setActionBar((Toolbar) findViewById(R.id.toolbar));
        mEditBody = (EditText) findViewById(R.id.body);
        findViewById(R.id.share).setOnClickListener(mOnClickListener);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.share:
                    share();
                    break;
            }
        }
    };

    public void SendSMS(View v) {
        Intent i = new Intent();
        i.setAction("com.example.android.directshare.SEND_SMS");
        String phoneNumber = ((EditText) findViewById(R.id.phoneNumber)).getText().toString();
        String msg = "testing message";
        i.putExtra("PHONE_NUMBER", phoneNumber);
        i.putExtra("TEXT_MSG", msg);
        i.setPackage(this.getPackageName());
        Log.d("send", "send sms");
        startService(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("send", "Permission granted");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    /**
     * Emits a sample share {@link Intent}.
     */
    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mEditBody.getText().toString());
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.send_intent_title)));
    }



}
