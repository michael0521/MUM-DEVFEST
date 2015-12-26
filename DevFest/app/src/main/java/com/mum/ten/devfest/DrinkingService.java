package com.mum.ten.devfest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

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
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        System.out.println("DrinkingService is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("Drinking Service is Running");

        Bitmap ayurveda = BitmapFactory.decodeResource(getResources(), R.drawable.ayurveda);
        ayurveda = Bitmap.createScaledBitmap(ayurveda, 200, 200, false);

        // Creates an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(this, MainActivity.class);

        // This ensures that the back button follows the recommended
        // convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notify = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setTicker("An new Alarm For You!")
                .setSmallIcon(R.drawable.bell)
                .setLargeIcon(ayurveda)
                .setContentIntent(resultPendingIntent)
                .setContentTitle("Drinking Time")
                .setContentText("Hi, your body needs a glass of water.")
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.zenbell))
                        //.setSound(Uri.parse("android.resource" + "://" + getPackageName() + "/" + R.raw.zenbell))
                .setDefaults(NotificationCompat.DEFAULT_LIGHTS | NotificationCompat.DEFAULT_VIBRATE)
                .setWhen(System.currentTimeMillis());
        //.build();

        nm.notify(NOTIFICATION_ID, notify.build());

        //stopService(intent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Drinking Service is Destroyed");
    }
}
