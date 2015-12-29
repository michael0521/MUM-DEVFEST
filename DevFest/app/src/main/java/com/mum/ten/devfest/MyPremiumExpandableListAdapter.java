package com.mum.ten.devfest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 12/26/2015.
 */
public class MyPremiumExpandableListAdapter extends BaseExpandableListAdapter{
    static final int products_total = 4;

    static final int images[] = {R.drawable.product1, R.drawable.product2, R.drawable.product3, R.drawable.product4};

    static final String products[] = {
            "Amrit Kalash Ambrosia | For super antioxidant power, 60 Herbal Tablets",
            "Aci-Balance | To help balance stomach acidity, 60 Herbal Tablets",
            "Aller-GI | Supports natural resistance to food allergens, 60 Vegetarian Capsules",
            "Blissful Sleep | To improve sleep quality, 30 Herbal Tablets"
    };

    private Context mContext;

    public MyPremiumExpandableListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return products_total;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return "Products";

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return products[childPosition];
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
            convertView = inflater.inflate(R.layout.act_item_child_withpic, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setText(getChild(groupPosition, childPosition).toString());
        ImageView icon = (ImageView) convertView.findViewById(R.id.lap_icon);
        icon.setImageResource(images[childPosition]);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
