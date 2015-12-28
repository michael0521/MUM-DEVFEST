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
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserInfo extends Fragment {

    Button btn_previous;
    Button btn_next;
    ViewPager mViewPager;
    EditText edit_first_name;
    EditText edit_last_name;
    EditText edit_password;
    EditText edit_age;
    Spinner spinner;
    ArrayList<String> items=new ArrayList<String>();

    public FragmentUserInfo() {
        // Required empty public constructor
    }

    public FragmentUserInfo setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_user_info, container, false);
        btn_previous = (Button)view.findViewById(R.id.btn_previous);
        btn_next = (Button)view.findViewById(R.id.btn_next);
        edit_first_name = (EditText)view.findViewById(R.id.edit_first_name);
        edit_last_name = (EditText)view.findViewById(R.id.edit_last_name);
        edit_password = (EditText)view.findViewById(R.id.edit_password);
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
        SharedPreferences.Editor e = mSharedPreferences.edit();

        int age = Integer.valueOf(mSharedPreferences.getString("age", "25"));
        int editAge;
        try {
            editAge = Integer.valueOf(edit_age.getText().toString());
        }catch(NumberFormatException ex){
            editAge = 25;
        }
        if(editAge < 1) editAge = 1;
        if(editAge > 130) editAge = 130;
        if(editAge != age){
            e.putString("age", Integer.valueOf(editAge).toString());
            e.commit();
        }

        String gender = mSharedPreferences.getString("gender", "");
        int index = spinner.getSelectedItemPosition();
        String selectedGender;
        if (index == 0){
            selectedGender = "Male";
        }else{
            selectedGender = "Female";
        }
        if(!selectedGender.equals(gender)){
            e.putString("gender", selectedGender);
            e.commit();
        }

        String firstName = mSharedPreferences.getString("firstName", "");
        String lastName = mSharedPreferences.getString("lastName", "");
        String password = mSharedPreferences.getString("password", "");
        if(!firstName.equals(edit_first_name.getText().toString())){
            e.putString("firstName", selectedGender);
            e.commit();
        }
        if(!lastName.equals(edit_last_name.getText().toString())){
            e.putString("lastName", selectedGender);
            e.commit();
        }
        if(!password.equals(edit_password.getText().toString())){
            e.putString("password", selectedGender);
            e.commit();
        }

    }
}
