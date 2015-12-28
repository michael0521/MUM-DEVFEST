package com.mum.ten.devfest;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGender extends Fragment {
    Button btn_next;
    ViewPager mViewPager;
    Spinner spinner;
    ArrayList<String> items=new ArrayList<String>();

    public FragmentGender() {
        // Required empty public constructor
    }

    public FragmentGender setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_gender, container, false);
        btn_next = (Button)view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto  gender fragment
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });

        items.clear();
        items.add(getResources().getString(R.string.gender_male));
        items.add(getResources().getString(R.string.gender_female));
        spinner = (Spinner)view.findViewById(R.id.spinner_gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("Devfest", 0);
        String gender = mSharedPreferences.getString("gender", "Empty");
        int index = spinner.getSelectedItemPosition();
        String selectedGender;
        if (index == 0){
            selectedGender = "Male";
        }else{
            selectedGender = "Female";
        }
        if(!selectedGender.equals(gender)){
            SharedPreferences.Editor e = mSharedPreferences.edit();
            e.putString("gender", selectedGender);
            e.commit();
        }
    }
}
