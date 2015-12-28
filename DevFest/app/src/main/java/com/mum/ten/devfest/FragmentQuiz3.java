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
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentQuiz3 extends Fragment {
    Button btn_previous;
    Button btn_next;
    ViewPager mViewPager;
    Spinner spinner;
    TextView title;
    ArrayList<String> items=new ArrayList<String>();

    public FragmentQuiz3() {
        // Required empty public constructor
    }

    public FragmentQuiz3 setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_quiz, container, false);

        title = (TextView)view.findViewById(R.id.label_quiz_name);
        btn_previous = (Button)view.findViewById(R.id.btn_previous);
        btn_next = (Button)view.findViewById(R.id.btn_next);
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
                //goto  gender fragment
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });

        title.setText("I prefer a cold climate to a warm one");
        items.clear();
        items.add(getResources().getString(R.string.select_item_always));
        items.add(getResources().getString(R.string.select_item_sometimes));
        items.add(getResources().getString(R.string.select_item_few));
        spinner = (Spinner)view.findViewById(R.id.spinner_quiz_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("Devfest", 0);
        int dry = mSharedPreferences.getInt("climate", 0);

        if(dry != spinner.getSelectedItemPosition()){
            SharedPreferences.Editor e = mSharedPreferences.edit();
            e.putInt("climate", spinner.getSelectedItemPosition());
            e.commit();
        }
    }
}
