package com.mum.ten.devfest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private Switch drinking_switcher;
    private Switch tm_switcher;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        displayUserInfo();

        drinking_switcher = (Switch)findViewById(R.id.drinking_switcher);
        tm_switcher = (Switch)findViewById(R.id.tm_switcher);

        preferences = getSharedPreferences("Devfest", MODE_PRIVATE);

        drinking_switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                editor = preferences.edit();
                editor.putBoolean("drinkingOption", isChecked);
                editor.commit();
                String[] drinkingTimes = {"10:19", "23:48", "23:20"};
                if (isChecked == true) {
                    //System.out.println("drinking switcher is changed to " + isChecked);
                    boolean drinkingOption = preferences.getBoolean("drinkingOption", false);
                    System.out.println("drinkingOption is set to " + drinkingOption);

                    Intent intent = new Intent(SettingsActivity.this, DrinkingService.class);

                    AlarmManager drinkingAm = (AlarmManager) getSystemService(ALARM_SERVICE);
                    setAlarmManagers(drinkingTimes, intent, drinkingAm);
                } else {
                    System.out.println("drinking switcher is changed to " + isChecked);
                }
            }
        });


        tm_switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                editor = preferences.edit();
                editor.putBoolean("meditationOption", isChecked);
                editor.commit();
                String[] tmTimes = {"10:19", "23:49", "23:38"};
                if (isChecked == true) {
                    //System.out.println("meditation switcher is changed to " + isChecked);
                    boolean meditationOption = preferences.getBoolean("meditationOption", false);
                    System.out.println("meditationOption is set to " + meditationOption);

                    Intent intent = new Intent(SettingsActivity.this, MeditationService.class);

                    AlarmManager tmAm = (AlarmManager) getSystemService(ALARM_SERVICE);
                    setAlarmManagers(tmTimes, intent, tmAm);
                } else {
                    System.out.println("meditation switcher is changed to " + isChecked);
                }
            }
        });
    }

    public void displayUserInfo(){
        //preferences.getString()
        TextView gender_value = (TextView)findViewById(R.id.gender_val);
        gender_value.setText("Male");
        TextView age_value = (TextView)findViewById(R.id.age_val);
        age_value.setText("33");
        TextView dosha_value = (TextView)findViewById(R.id.type_val);
        dosha_value.setText("Pitta");
    }


    public void setAlarmManagers(String[] eventTimes, Intent itent, AlarmManager am) {

        for (int i = 0; i < eventTimes.length; i++) {
            PendingIntent pendingIntent = PendingIntent.getService(
                    SettingsActivity.this, i, itent, 0);

            String[] hourMin = eventTimes[i].split(":");
            int hour = Integer.parseInt(hourMin[0]);
            int mins = Integer.parseInt(hourMin[1]);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, mins);

            if (calendar.getTimeInMillis() >= System.currentTimeMillis()) {
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                System.out.println("New alarm is set! " + i);
            } else {
                if (am != null)
                    am.cancel(pendingIntent);
            }
        }

    }
}
