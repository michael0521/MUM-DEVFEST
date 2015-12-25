package com.mum.ten.devfest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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




}

