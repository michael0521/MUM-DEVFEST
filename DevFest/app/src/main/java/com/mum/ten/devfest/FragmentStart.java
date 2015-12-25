package com.mum.ten.devfest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStart extends Fragment {

    Button btn_start;
    ViewPager mViewPager;
    public FragmentStart() {
        // Required empty public constructor
    }

    public FragmentStart setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_start, container, false);
        btn_start = (Button)view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto  gender fragment
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });
        return view;
    }

}
