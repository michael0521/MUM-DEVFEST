package com.mum.ten.devfest;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAge extends Fragment {

    Button btn_previous;
    Button btn_next;
    ViewPager mViewPager;
    EditText edit_age;

    public FragmentAge() {
        // Required empty public constructor
    }

    public FragmentAge setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_age, container, false);
        btn_previous = (Button)view.findViewById(R.id.btn_previous);
        btn_next = (Button)view.findViewById(R.id.btn_next);
        edit_age = (EditText)view.findViewById(R.id.edit_age);
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto gender fragment
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto finish fragment
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("Devfest", 0);
        int age = mSharedPreferences.getInt("age", 25);
        int editAge = Integer.valueOf(edit_age.getText().toString());
        if(editAge > 0 && editAge != age){
            SharedPreferences.Editor e = mSharedPreferences.edit();
            e.putInt("age",editAge);
            e.commit();
        }
    }
}
