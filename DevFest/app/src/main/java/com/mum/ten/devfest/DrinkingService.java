package com.mum.ten.devfest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by yangxiaoming on 12/24/2015.
 */
public class DrinkingService extends Service {

    static final int NOTIFICATION_ID = 0x123;

    private NotificationManager nm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        System.out.println("DrinkingService is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        System.out.println("Drinking Service is Running");

        Notification notify = new Notification.Builder(this)
                .setAutoCancel(true)
                .setTicker("New Alert")
                .setSmallIcon(R.drawable.bell)
                .setContentTitle("Drinking Time")
                .setContentText("Hi, your body needs a glass of water")
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.zenbell))
                 //.setSound(Uri.parse("android.resource" + "://" + getPackageName() + "/" + R.raw.zenbell))
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setWhen(System.currentTimeMillis())
                .build();

        nm.notify(NOTIFICATION_ID, notify);

        //stopService(intent);
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("Drinking Service is Destroyed");
    }
}
