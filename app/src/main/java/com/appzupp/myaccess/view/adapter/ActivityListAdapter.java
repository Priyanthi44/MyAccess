package com.appzupp.myaccess.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appzupp.myaccess.R;

import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    List<String> mActivityNamesList;
    List<Integer> mActivityImageList;
    LayoutInflater mLayoutInflater;
    OnActivityListener mOnActivityListener;

    public ActivityListAdapter(List<String> activityNames, List<Integer> activityImages, Context context, OnActivityListener onActivityListener) {
        this.mActivityNamesList = activityNames;
        this.mActivityImageList = activityImages;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mOnActivityListener=onActivityListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.activity_list_grid_item, parent, false);

        return new ViewHolder(view,mOnActivityListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.activityText.setText(mActivityNamesList.get(position));
        holder.activityImage.setImageResource(mActivityImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mActivityNamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView activityImage;
        TextView activityText;
OnActivityListener mOnActivityListener;

        public ViewHolder(@NonNull View itemView, OnActivityListener onActivityListener) {
            super(itemView);
            this.activityImage = itemView.findViewById(R.id.activity_image_view);
            this.activityText = itemView.findViewById(R.id.activity_name);
            this.mOnActivityListener=onActivityListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mOnActivityListener.onActivityClick(getAdapterPosition());
        }
    }
    public interface OnActivityListener{
        void onActivityClick(int position);
    }
}
