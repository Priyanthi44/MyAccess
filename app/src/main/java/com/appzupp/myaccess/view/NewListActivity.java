
package com.appzupp.myaccess.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.appzupp.myaccess.R;
import com.appzupp.myaccess.model.Activity;
import com.appzupp.myaccess.view.adapter.ActivityListAdapter;
import com.appzupp.myaccess.view.widgets.NewActivity;
import com.appzupp.myaccess.viewmodel.ActivityListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewListActivity extends AppCompatActivity {
    private NewActivity mNewActivity;

    private List<String> mActivityNamesList;
    private List<Integer> mActivityImagesList;
    private List<Activity> mActivities;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private ActivityListAdapter mActivityListAdapter;
    private DocumentSnapshot documentSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_new_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.activity_list);

        mActivities = new ArrayList<>();
        mActivityNamesList = new ArrayList<>();
        mActivityImagesList = new ArrayList<>();


        ActivityListViewModel mViewModel = new ViewModelProvider(this).get(ActivityListViewModel.class);
        mViewModel.getActivities().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                if (mActivities.size() > 0) {
                    mActivities.clear();
                }
                if (activities != null) {
                    mActivities.addAll(activities);
                }
                if (mActivityListAdapter != null)
                    mActivityListAdapter.notifyDataSetChanged();
            }
        });

    }
}