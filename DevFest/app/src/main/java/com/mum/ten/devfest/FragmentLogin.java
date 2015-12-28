package com.mum.ten.devfest;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment {

    Button btn_join;
    Button btn_sign;
    EditText edit_firstName;
    EditText edit_lastName;
    EditText edit_password;
    ViewPager mViewPager;

    public FragmentLogin() {
        // Required empty public constructor
    }

    public FragmentLogin setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_login, container, false);

        edit_firstName = (EditText)view.findViewById(R.id.edit_first_name);
        edit_lastName = (EditText)view.findViewById(R.id.edit_last_name);
        edit_password = (EditText)view.findViewById(R.id.edit_password);
        btn_join = (Button)view.findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });

        btn_sign = (Button)view.findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto main activity
                if(edit_password.getText().toString().trim().isEmpty()
                        || edit_lastName.getText().toString().trim().isEmpty()
                        || edit_firstName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(), "Please input name and password,thank you!!",Toast.LENGTH_SHORT);
                    return;
                }
                Toast.makeText(getActivity(), "Success!!",Toast.LENGTH_SHORT);
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("Devfest", 0);
                SharedPreferences.Editor e = mSharedPreferences.edit();
                e.putBoolean("ifEntryWelcome", true);
                e.commit();

                startAlarmService();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void startAlarmService(){

        SharedPreferences preferences = getActivity().getSharedPreferences("Devfest", Context.MODE_PRIVATE);
        boolean isCheckedDrink = preferences.getBoolean("drinkingOption", true);
        boolean isCheckedTM = preferences.getBoolean("meditationOption", true);
        if (isCheckedDrink == true) {
            Intent intent2 = new Intent(getActivity(), DrinkingService.class);
            AlarmManager drinkingAm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            setAlarmManagers(getActivity(), SettingsActivity.drinkingTimes, intent2, drinkingAm);
        }

        if (isCheckedTM == true) {
            Intent intent2 = new Intent(getActivity(), MeditationService.class);
            AlarmManager tmAm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            setAlarmManagers(getActivity(), SettingsActivity.tmTimes, intent2, tmAm);
        }

    }

    public void setAlarmManagers(Context context, String[] eventTimes, Intent intent, AlarmManager am) {

        for (int i = 0; i < eventTimes.length; i++) {
            PendingIntent pendingIntent = PendingIntent.getService(
                    context, i, intent, 0);

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
