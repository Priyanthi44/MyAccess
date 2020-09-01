package com.appzupp.myaccess.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appzupp.myaccess.R;
import com.appzupp.myaccess.firebase.IActivityList;
import com.appzupp.myaccess.model.Activity;
import com.appzupp.myaccess.view.adapter.ActivityListAdapter;
import com.appzupp.myaccess.view.widgets.NewActivity;
import com.appzupp.myaccess.view.widgets.UpdateActivity;
import com.appzupp.myaccess.viewmodel.ActivityListViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityList extends AppCompatActivity implements IActivityList,
        SwipeRefreshLayout.OnRefreshListener, ActivityListAdapter.OnActivityListener {
    private NewActivity mNewActivity;
    private UpdateActivity mUpdateActivity;

    private NotificationManagerCompat notificationManager;
    private List<String> mActivityNamesList;
    private List<Integer> mActivityImagesList;
    private List<Activity> mActivities;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private ActivityListAdapter mActivityListAdapter;
    private DocumentSnapshot documentSnapshot;

    public static final String CHANNEL_ID = "channel";
    public static final String CHANNEL_2_ID = "channel2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recyclerview);
        mSwipeRefresh = findViewById(R.id.swipe_refresh_layout);

        mActivities = new ArrayList<>();
        mActivityNamesList = new ArrayList<>();
        mActivityImagesList = new ArrayList<>();
        mSwipeRefresh.setOnRefreshListener(this);


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                deleteActivity(mActivities.get(viewHolder.getAdapterPosition()));
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        notificationManager = NotificationManagerCompat.from(this);
        // getActivityList();

        ActivityListViewModel mViewModel = new ViewModelProvider(this).get(ActivityListViewModel.class);
        mViewModel.getActivities().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                if (mActivities.size() > 0) {
                    mActivities.clear();
                }
                if (activities != null) {
                    if (activities.size() > 0) {
                        mActivities.addAll(activities);
                        setActivities();
                    }
                }
                if (mActivityListAdapter != null)
                    mActivityListAdapter.notifyDataSetChanged();
            }
        });

        notificationManager = NotificationManagerCompat.from(this);


        initRecycleView();
        createNotificationChannels();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewActivity = new NewActivity();
                mNewActivity.show(getSupportFragmentManager(), "New Activity");

            }
        });

    }

    private void initRecycleView() {
        setActivities();
        // getActivityList();
        if (mActivityListAdapter == null) {
            mActivityListAdapter = new ActivityListAdapter(mActivityNamesList, mActivityImagesList, this, this);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mRecyclerView.setAdapter(mActivityListAdapter);
        }
    }

    private void setActivities() {

        for (Activity activity : mActivities) {
            mActivityNamesList.add(activity.getActivity_name());
            mActivityImagesList.add(activity.getActivity_image());

        }


    }

    @Override
    public void createNewActivity(String activityName, int activityImage, String activityInstructions) {
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newActivityRef = db.collection("activities").document();

        String userID = FirebaseAuth.getInstance().getUid();
        Activity activity = new Activity();
        activity.setActivity_name(activityName);
        activity.setActivity_image(activityImage);
        activity.setInstructions(activityInstructions);
        activity.setActivity_status(false);
        activity.setActivity_id(newActivityRef.getId());
        activity.setUser(userID);

        newActivityRef.set(activity).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ActivityList.this, "Created a new activity", Toast.LENGTH_SHORT).show();
                    mActivityListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ActivityList.this, "Activity failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getActivityList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference activitiesCollectionReference = db.collection("activities");

        Query query = activitiesCollectionReference
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid());


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Activity activity = document.toObject(Activity.class);
                        if (!activity.isActivity_status()) {
                            mActivities.add(activity);
                            mActivityNamesList.add(activity.getActivity_name());
                            mActivityImagesList.add(activity.getActivity_image());
                        }

                    }
                    mActivityListAdapter.notifyDataSetChanged();
                } else {
                    Activity activity = new Activity();
                    activity.setActivity_id("Query Failed");
                }
            }
        });
    }

    @Override
    public void deleteActivity(final Activity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference noteRef = db
                .collection("activities")
                .document(activity.getActivity_id());

        noteRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), activity.getActivity_name() + " deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Delete Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void updateActivity(Activity activity) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference actRef = db
                .collection("activities")
                .document(activity.getActivity_id());

        actRef.update(
                "activity_status", activity.isActivity_status()
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Activity Updated", Toast.LENGTH_SHORT).show();
                    mActivityListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (activity.isActivity_status()) {
            viewholder.getActivityText().setTextColor(Color.GREEN);
        } else {
            viewholder.getActivityText().setTextColor(Color.RED);
        }
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(activity.getActivity_name())
                .setContentText("Successfully Completed")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    @Override
    public void onRefresh() {

        mActivityNamesList.removeAll(mActivityNamesList);
        mActivityImagesList.removeAll(mActivityImagesList);
        getActivityList();
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onActivityClick(int position) {
        Activity activity = mActivities.get(position);
        mUpdateActivity = new UpdateActivity(activity);
        mUpdateActivity.show(getSupportFragmentManager(), "New Activity");
        viewholder = (ActivityListAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);


    }

    ActivityListAdapter.ViewHolder viewholder;

    private void changeColor(boolean activity_status, int position) {

    }
}