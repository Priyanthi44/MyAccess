package com.appzupp.myaccess.view.widgets;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.appzupp.myaccess.R;
import com.appzupp.myaccess.view.widgets.spinner.ActivitySpinnerAdapter;
import com.appzupp.myaccess.firebase.IActivityList;
import com.appzupp.myaccess.view.widgets.spinner.ActivityItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;


public class NewActivity extends DialogFragment implements View.OnClickListener {

    private IActivityList iActivityList;

    private ArrayList<ActivityItem> mActivityList;
    private ActivitySpinnerAdapter mAdapter;

    private Spinner mSpinner;
    private TextInputEditText mContent;
    private ActivityItem mactivityItemitem;


    private TextView mCreate, mCancel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_DeviceDefault_Light_Dialog;
        setStyle(style, theme);
        initList();


    }

    private void initList() {
        mActivityList = new ArrayList<>();
        mActivityList.add(new ActivityItem("Brush Teeth", R.drawable.brushteeth));
        mActivityList.add(new ActivityItem("Floss", R.drawable.floss));
        mActivityList.add(new ActivityItem("Wash Face", R.drawable.washface));
        mActivityList.add(new ActivityItem("Breakfast", R.drawable.breakfast));
        mActivityList.add(new ActivityItem("Beverage", R.drawable.fluid));
        mActivityList.add(new ActivityItem("Lunch", R.drawable.food));
        mActivityList.add(new ActivityItem("Dinner", R.drawable.dinner));
        mActivityList.add(new ActivityItem("Exercise", R.drawable.exercise));
        mActivityList.add(new ActivityItem("Shower", R.drawable.shower));
        mActivityList.add(new ActivityItem("Sleep", R.drawable.sleep));
        mActivityList.add(new ActivityItem("Work", R.drawable.work));
        mActivityList.add(new ActivityItem("Study", R.drawable.study));

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_activity, container, false);
        mSpinner = view.findViewById(R.id.spinner_activity);
        mAdapter = new ActivitySpinnerAdapter(this.getContext(), mActivityList);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mactivityItemitem = (ActivityItem) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mContent = view.findViewById(R.id.activity_instructions);
        mCreate = view.findViewById(R.id.create);
        mCancel = view.findViewById(R.id.cancel);

        mCancel.setOnClickListener(this);
        mCreate.setOnClickListener(this);

        getDialog().setTitle("Add New Activity");
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create: {
                String activityName = mactivityItemitem.getmActivityName();
                String activityInstructions = mContent.getText().toString();
                int activityImage=mactivityItemitem.getmActivityImage();
                iActivityList.createNewActivity(activityName, activityImage,activityInstructions);
                if (activityName != null) {
                    Objects.requireNonNull(getDialog()).dismiss();
                } else
                    Toast.makeText(getContext(), "Select an Activity", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cancel: {
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
