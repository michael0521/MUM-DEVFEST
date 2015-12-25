package com.mum.ten.devfest;


import android.content.Intent;
import android.content.SharedPreferences;
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
public class FragmentFinish extends Fragment {

    Button btn_finish;
    ViewPager mViewPager;
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
        btn_finish = (Button)view.findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish quiz and goto main activity
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("Devfest", 0);
                SharedPreferences.Editor e = mSharedPreferences.edit();
                e.putBoolean("ifEntryWelcome", true);
                e.commit();

                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}
