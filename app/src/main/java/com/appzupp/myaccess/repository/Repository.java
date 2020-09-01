package com.appzupp.myaccess.repository;

import androidx.annotation.NonNull;

import com.appzupp.myaccess.firebase.IActivityList;
import com.appzupp.myaccess.model.Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IActivityList {

public List<Activity> mActivityList;
    @Override
    public void createNewActivity(String activityName, int activityImage, String activityInstructions) {

    }

    @Override
    public void getActivityList() {
        mActivityList= new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference activitiesCollectionReference = db.collection("activities");

        Query query = activitiesCollectionReference
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .orderBy("timestamp", Query.Direction.ASCENDING);


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Activity activity = document.toObject(Activity.class);
                        if(!activity.isActivity_status())
                       mActivityList.add(activity);

                    }

                } else {
                    Activity activity = new Activity();
                    activity.setActivity_id("Query Failed");
                }
            }
        });


    }

    @Override
    public void deleteActivity(Activity activity) {

    }

    @Override
    public void updateActivity(Activity activity) {

    }


}
