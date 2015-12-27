package com.mum.ten.devfest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyLaunchReceiver extends BroadcastReceiver {
    public MyLaunchReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            //start service
        }
    }
}
