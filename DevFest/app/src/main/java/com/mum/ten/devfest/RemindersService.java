package com.mum.ten.devfest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * Created by yangxiaoming on 12/23/2015.
 */
public class RemindersService extends Service {

    private AlarmManager[] alarmManagers;
    private AlarmManager drikingAm;
    private AlarmManager tmAm;
    private int rq = 0;



    private SharedPreferences preferences;

    private boolean drinkingIsChecked;
    private boolean meditationIsChecked;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("RemindersService is created");
        drikingAm = (AlarmManager) getSystemService(ALARM_SERVICE);
        tmAm = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("RemindersService is running");

        preferences = getSharedPreferences("Devfest", MODE_PRIVATE);

        drinkingIsChecked = preferences.getBoolean("drinkingOption", false);
        System.out.println("Drinking setting from preference is " + drinkingIsChecked);
        meditationIsChecked = preferences.getBoolean("meditationOption", false);
        System.out.println("Meditation setting from preference is " + meditationIsChecked);


        //String[] drinkingTimes = {"7:00", "9:00", "11:30", "13:30", "15:00", "17:00", "20:00", "22:00"};
        String[] drinkingTimes = {"10:19", "22:36", "22:37"};
        String[] tmTimes = {"10:19", "21:39", "17:00"};
        //alarmManagers = new AlarmManager[drinkingTimes.length];

        PendingIntent pendingIntent = null;

//        if (drinkingIsChecked) {
//            int drinkCode = 0;
//            Intent drink = new Intent(RemindersService.this
//                    , DrinkingService.class);
//
//            setAlarmManagers(drinkingTimes, pendingIntent, drink, drikingAm, drinkCode);
//
//        } else {
//            if (drikingAm != null) {
//                drikingAm.cancel(pendingIntent);
//            }
//        }

        if (meditationIsChecked) {
            int tmCode = 100;
            Intent meditation = new Intent(RemindersService.this
                    , MeditationService.class);

            setAlarmManagers(tmTimes, pendingIntent, meditation, tmAm, tmCode);

        } else {
            if (tmAm != null) {
                tmAm.cancel(pendingIntent);
            }
        }

        return START_STICKY;
    }

    public void setAlarmManagers(String[] eventTimes, PendingIntent pendingIntent, Intent itent, AlarmManager am, int k) {

        for (int i = 0; i < eventTimes.length; i++) {
            pendingIntent = PendingIntent.getService(
                    RemindersService.this, ++k, itent, 0);

            String[] hourMin = eventTimes[i].split(":");
            int hour = Integer.parseInt(hourMin[0]);
            int mins = Integer.parseInt(hourMin[1]);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, mins);

            if (calendar.getTimeInMillis() >= System.currentTimeMillis()) {
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                System.out.println("New alarm is set! " + k);
            } else {
                if (am != null)
                    am.cancel(pendingIntent);
            }
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Reminders Service is Destroyed");
    }
}
