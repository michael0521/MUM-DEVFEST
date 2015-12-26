package com.mum.ten.devfest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Switch switcher;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switcher = (Switch)findViewById(R.id.switcher);

        preferences = getSharedPreferences("Devfest", MODE_PRIVATE);

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                editor = preferences.edit();
                editor.putBoolean("drinkingOption", isChecked);
                editor.commit();

                if(isChecked == true) {
                    System.out.println("drinking switcher is changed to " + isChecked);
                    boolean drinkingOption = preferences.getBoolean("drinkingOption", false);
                    System.out.println("drinkingOption is set to " + drinkingOption);
                    Intent intent = new Intent();
                    intent.putExtra("drinking", isChecked);
                    intent.setAction("REMINDER_SERVICE");
                    startService(intent);
                }else {
                    System.out.println("drinking switcher is changed to " + isChecked);
                }
            }
        });
    }
}
