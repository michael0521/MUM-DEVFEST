package com.mum.ten.devfest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 12/26/2015.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter{
    static final int ALERT_TOTAL = 3;
    static final int ALERT_DRINK = 0;
    static final int ALERT_TM = 1;
    static final int ALERT_FOOD = 2;

    private Context mContext;

    public MyExpandableListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return ALERT_TOTAL;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(groupPosition == ALERT_DRINK)
            return mContext.getResources().getString(R.string.action_item_title_drink);
        else if (groupPosition == ALERT_TM)
            return mContext.getResources().getString(R.string.action_item_title_TM);
        else if (groupPosition == ALERT_FOOD)
            return mContext.getResources().getString(R.string.action_item_title_food);
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(groupPosition == ALERT_DRINK)
            return mContext.getResources().getString(R.string.action_item_subtitle_drink);
        else if (groupPosition == ALERT_TM)
            return mContext.getResources().getString(R.string.action_item_subtitle_TM);
        else if (groupPosition == ALERT_FOOD)
            return mContext.getResources().getString(R.string.action_item_subtitle_food);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.act_item_group, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop_group);
        item.setText(this.getGroup(groupPosition).toString());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.act_item_child, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setText(getChild(groupPosition, childPosition).toString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
