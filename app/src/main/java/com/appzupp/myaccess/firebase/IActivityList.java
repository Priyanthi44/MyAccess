package com.appzupp.myaccess.firebase;

import com.appzupp.myaccess.model.Activity;

import java.util.List;

public interface IActivityList {
   void createNewActivity(String activityName, int activityImage,
                          String activityInstructions) ;



    void getActivityList()

    ;
    void deleteActivity(Activity activity)

    ;
    void updateActivity(Activity activity)

    ;
}
