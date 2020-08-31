package com.appzupp.myaccess.view.widgets.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appzupp.myaccess.R;
import com.appzupp.myaccess.view.widgets.spinner.ActivityItem;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ActivitySpinnerAdapter extends ArrayAdapter<ActivityItem> {
    public ActivitySpinnerAdapter(Context context, ArrayList<ActivityItem> activityList){
        super(context,0,activityList);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_spinner_row,parent,false);

        }
        ShapeableImageView activityImageView=convertView.findViewById(R.id.image_view_activity);
        MaterialTextView activityTextView=convertView.findViewById(R.id.text_view_activity);
        ActivityItem currentActivityItem=getItem(position);
        if(currentActivityItem!=null) {
            activityImageView.setImageResource(currentActivityItem.getmActivityImage());
            activityTextView.setText(currentActivityItem.getmActivityName());
        }
        return convertView;
    }


}
