package com.mum.ten.devfest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new FragmentStart().setViewPager(viewPager));
        fragmentList.add(new FragmentGender().setViewPager(viewPager));
        fragmentList.add(new FragmentAge().setViewPager(viewPager));
        fragmentList.add(new FragmentFinish().setViewPager(viewPager));

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);

    }

    @Override
    public void onBackPressed() {
        Log.d("@@@", "run in onBackPressed");
//        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle(R.string.fragment_exit_dialog_title)
                .setMessage(R.string.fragment_exit_dialog_message)
                .setNegativeButton(R.string.fragment_exit_dialog_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("@@@", "run in Negative");
                    }
                })
                .setPositiveButton(R.string.fragment_exit_dialog_button_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();
    }
}

