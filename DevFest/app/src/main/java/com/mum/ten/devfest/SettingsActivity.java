package com.mum.ten.devfest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {
    static final String[] drinkingTimes = {"09:38","10:38", "11:39", "12:40", "14:39", "15:40","16:38", "17:39", "18:40", "20:38"};
    static final String[] tmTimes = {"07:19", "12:20", "15:21"};

    private Switch drinking_switcher;
    private Switch tm_switcher;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private int DATA_COUNT = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("Devfest", MODE_PRIVATE);
        displayUserInfo();

        drinking_switcher = (Switch)findViewById(R.id.drinking_switcher);
        tm_switcher = (Switch)findViewById(R.id.tm_switcher);

        drinking_switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                editor = preferences.edit();
                editor.putBoolean("drinkingOption", isChecked);
                editor.commit();
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


        BarChart chart_bar = (BarChart)findViewById(R.id.chart_bar);
        chart_bar.setData(getBarData());
    }

    public void displayUserInfo(){
        //preferences.getString()
        TextView gender_value = (TextView)findViewById(R.id.gender_val);
//        gender_value.setText("Male");
        gender_value.setText(preferences.getString("gender","Male"));
        TextView age_value = (TextView)findViewById(R.id.age_val);
//        age_value.setText("25");
        age_value.setText(preferences.getString("age","25"));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private BarData getBarData(){
        BarDataSet dataSetA = new BarDataSet(getChartData(), getString(R.string.app_name));
        //設定顏色
        dataSetA.setColors(getChartColors());
        //設定顯示字串
        dataSetA.setStackLabels(getStackLabels());

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA); // add the datasets

        return new BarData(getLabels(), dataSets);
    }

    private String[] getStackLabels(){
        return new String[]{getString(R.string.chart_label_drink),
                getString(R.string.chart_label_tm),
                getString(R.string.chart_label_diet),
                //getString(R.string.chart_label_stretch)};
                getString(R.string.chart_label_stretch)};
    }

    private int[] getChartColors() {
        int[] colors = new int[]{getResourceColor(R.color.chart_color_drink),
                getResourceColor(R.color.chart_color_tm),
                getResourceColor(R.color.chart_color_diet)
                //,getResourceColor(R.color.chart_color_stretch) };
                ,getResourceColor(R.color.chart_color_stretch) };
        return colors;
    }

    private int getResourceColor(int resID){
        return getResources().getColor(resID);
    }

    private ArrayList<BarEntry> getChartData(){
        final int DATA_COUNT = 7;

        ArrayList<BarEntry> chartData = new ArrayList<>();

        for(int i=0;i<DATA_COUNT;i++){
            float credit_drink = i*5;
            float credit_tm = i*2;
            float credit_diet = i*2;
            float credit_strech = i*4;
            //chartData.add(new BarEntry(new float[]{credit_strech, credit_diet, credit_tm, credit_drink}, i));
            chartData.add(new BarEntry(new float[]{credit_diet, credit_tm, credit_drink}, i));
        }
        return chartData;
    }

    private ArrayList<String> getLabels(){
        ArrayList<String> chartLabels = new ArrayList<>();
//        for(int i=0;i<DATA_COUNT;i++){
//            chartLabels.add("D"+i);
//        }
        chartLabels.add("Mon");
        chartLabels.add("Tue");
        chartLabels.add("Wen");
        chartLabels.add("Thu");
        chartLabels.add("Fri");
        chartLabels.add("Sat");
        chartLabels.add("Sun");
        return chartLabels;
    }
}
