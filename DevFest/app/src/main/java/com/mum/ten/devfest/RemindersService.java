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

    private SharedPreferences preferences;

    boolean drinkingIsChecked;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        System.out.println("RemindersService is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        System.out.println("RemindersService is running");

        if(intent != null) {
            drinkingIsChecked = intent.getBooleanExtra("drinking", false);
            System.out.println("Drinking setting from intent is " + drinkingIsChecked);
        }else {
            preferences = getSharedPreferences("Devfest", MODE_PRIVATE);
            drinkingIsChecked = preferences.getBoolean("drinkingOption", false);
            System.out.println("Drinking setting from preference is " + drinkingIsChecked);
        }

        Intent drink = new Intent(RemindersService.this
                , DrinkingService.class);

        String[] drinkingTimes = {"7:00", "9:00", "11:30", "13:30", "15:00", "17:00", "20:00", "22:00"};
        //String[] drinkingTimes = {"10:19", "10:58", "11:04"};
        alarmManagers = new AlarmManager[drinkingTimes.length];
        PendingIntent piDrinking = null;
        if(drinkingIsChecked) {
           for(int i = 0; i < drinkingTimes.length; i++) {
               piDrinking = PendingIntent.getService(
                       RemindersService.this, i, drink, 0);
               String[] hourMin = drinkingTimes[i].split(":");
               int hour = Integer.parseInt(hourMin[0]);
               int mins = Integer.parseInt(hourMin[1]);

               Calendar calendar = Calendar.getInstance();
               calendar.setTimeInMillis(System.currentTimeMillis());
               calendar.set(Calendar.HOUR_OF_DAY, hour);
               calendar.set(Calendar.MINUTE, mins);

               if (alarmManagers[i] != null) {
                   alarmManagers[i].cancel(piDrinking);
                   System.out.println("pre-existing alarm canceled!");
               } else {
                   if(calendar.getTimeInMillis() >= System.currentTimeMillis()) {
                       alarmManagers[i] = (AlarmManager) getSystemService(ALARM_SERVICE);
                       alarmManagers[i].set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), piDrinking);
                       System.out.println("New alarm is set!");
                   }
               }
           }
        }else {
            for(int i = 0; i < drinkingTimes.length; i++) {
                if (alarmManagers[i]!= null) {
                    alarmManagers[i].cancel(piDrinking);
                }
            }
        }

        return START_STICKY;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("Reminders Service is Destroyed");
    }
}
