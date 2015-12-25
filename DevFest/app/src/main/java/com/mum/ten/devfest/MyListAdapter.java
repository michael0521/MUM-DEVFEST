package com.mum.ten.devfest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 12/24/2015.
 */

public class MyListAdapter extends BaseAdapter {
    static final int ALERT_TOTAL = 3;
    static final int ALERT_DRINK = 0;
    static final int ALERT_TM = 1;
    static final int ALERT_FOOD = 2;

    LinearLayout titleLayout;
    LinearLayout layout;
    TextView title;
    TextView subTitle;

    LayoutInflater mInflater;
    public MyListAdapter(Context context) {
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = (LinearLayout) mInflater.inflate(R.layout.act_item, null);
            titleLayout = (LinearLayout) convertView.findViewById(R.id.title);
            title = (TextView) convertView.findViewById(R.id.title_content);
            subTitle  = (TextView) convertView.findViewById(R.id.sub_title);
            if (position == ALERT_DRINK) {
                title.setText(R.string.action_item_title_drink);
                subTitle.setText(R.string.action_item_subtitle_drink);
            } else if (position == ALERT_TM) {
                title.setText(R.string.action_item_title_TM);
                subTitle.setText(R.string.action_item_subtitle_TM);
            } else if (position == ALERT_FOOD) {
                title.setText(R.string.action_item_title_food);
                subTitle.setText(R.string.action_item_subtitle_food);
            }
            subTitle.setVisibility(View.VISIBLE);
            titleLayout.setClickable(true);
            titleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout = (LinearLayout) mInflater.inflate(R.layout.act_item, null);
                    subTitle  = (TextView) layout.findViewById(R.id.sub_title);
                    if (subTitle.getVisibility() == View.VISIBLE) {
                        AnimationSet animationSet = new AnimationSet(true);
                        ScaleAnimation animation = new ScaleAnimation(1.0f,0,1.0f,0,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(1000);
                        subTitle.setAnimation(animation);
                        animation.startNow();
                        subTitle.setVisibility(View.GONE);
                        Log.d("@@@", "GONE");
                    } else {
                        AnimationSet animationSet = new AnimationSet(true);
                        ScaleAnimation animation = new ScaleAnimation(1.0f,1.0f,0,1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(1000);
                        subTitle.setAnimation(animation);
                        animation.startNow();
                        subTitle.setVisibility(View.VISIBLE);
                        subTitle.setText(R.string.action_item_subtitle_TM);
                        Log.d("@@@", "VISIBLE");
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return ALERT_TOTAL;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
