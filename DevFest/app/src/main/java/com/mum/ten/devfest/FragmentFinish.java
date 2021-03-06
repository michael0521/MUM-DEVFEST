package com.mum.ten.devfest;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFinish extends Fragment {

    TextView result;
    Button btn_finish;
    Button btn_previous;
    ViewPager mViewPager;
    ImageView result_img;

    String dosha_types[] = {"vata", "pitta", "kapha", "vata-pitta-kapha"};
    int drawables[] = {R.drawable.vata, R.drawable.pitta, R.drawable.kapha, R.drawable.dosha_vata_pitta_kapha};

    public FragmentFinish() {
        // Required empty public constructor
    }

    public FragmentFinish setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        return this;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_finish, container, false);

        double dd = Math.random();
        int index = (int)(dd*4);
        result = (TextView) view.findViewById(R.id.result_content);
        result.setText(String.format(getActivity().
                getResources().getString(R.string.fragment_finish_result), dosha_types[index]));

        result_img = (ImageView) view.findViewById(R.id.result_img);
        result_img.setImageResource(drawables[index]);

        btn_previous = (Button)view.findViewById(R.id.btn_previous);
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto gender fragment
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            }
        });
        btn_finish = (Button)view.findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish quiz and goto main activity
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("Devfest", 0);
                SharedPreferences.Editor e = mSharedPreferences.edit();
                e.putBoolean("ifEntryWelcome", true);
                e.commit();

                startAlarmService();

                Intent intent = new Intent(getActivity(),MainActivity.class);
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
