package com.appzupp.myaccess.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appzupp.myaccess.model.Activity;
import com.appzupp.myaccess.repository.Repository;

import java.util.List;

public class ActivityListViewModel extends ViewModel {
    private MutableLiveData<List<Activity>> activities;
    public LiveData<List<Activity>> getActivities() {
        if (activities == null) {
            activities = new MutableLiveData<List<Activity>>();
            loadActivities();
        }
         return activities;
    }

    private void loadActivities() {
        // Do an asynchronous operation to fetch users.
     Repository repo =new Repository();
     repo.getActivityList();
     activities.setValue(repo.mActivityList);
    }


}
