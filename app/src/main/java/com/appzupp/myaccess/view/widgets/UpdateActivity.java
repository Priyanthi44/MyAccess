package com.appzupp.myaccess.view.widgets;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.appzupp.myaccess.R;
import com.appzupp.myaccess.firebase.IActivityList;
import com.appzupp.myaccess.model.Activity;
import com.appzupp.myaccess.view.widgets.spinner.ActivityItem;
import com.appzupp.myaccess.view.widgets.spinner.ActivitySpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import static androidx.core.content.ContextCompat.getSystemService;

public class UpdateActivity extends DialogFragment implements View.OnClickListener {
    private IActivityList iActivityList;

    private ArrayList<ActivityItem> mActivityList;

    private ImageView mImageView;
    private Activity mActivityItem;
    private int mImage;

    private TextView mComplete, mNotComplete, mInstructions, mActivityName;



    public UpdateActivity(Activity activity) {
        this.mActivityItem = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_DeviceDefault_Light_Dialog;
        setStyle(style, theme);



    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_activity, container, false);
        mInstructions = view.findViewById(R.id.activity_instructions);
        mComplete = view.findViewById(R.id.completed);
        mActivityName = view.findViewById(R.id.image_view_activity);
        mImageView = view.findViewById(R.id.image_activity);
        mNotComplete = view.findViewById(R.id.not_completed);

        mComplete.setOnClickListener(this);
        mNotComplete.setOnClickListener(this);

        getDialog().setTitle("Update Activity");
        setProperties();
        return view;
    }

    private void setProperties() {
        mImageView.setImageResource(mActivityItem.getActivity_image());
        mActivityName.setText(mActivityItem.getActivity_name());
        mInstructions.setText(mActivityItem.getInstructions());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.completed: {
                //Update Record
                mActivityName.setBackgroundColor(Color.GREEN);
                iActivityList.updateActivity(mActivityItem);
                //Send notification


            }
            case R.id.not_completed: {
                //set color red
                mActivityName.setBackgroundColor(Color.RED);
                getDialog().dismiss();
                break;
            }

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iActivityList = (IActivityList) getActivity();
    }
}
