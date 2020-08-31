package com.appzupp.myaccess.view.widgets.spinner;

public class ActivityItem {
    private String mActivityName;
    private int mActivityImage;

    public ActivityItem(String activityName, int activityImage){
        this.mActivityImage=activityImage;
        this.mActivityName=activityName;
    }

    public int getmActivityImage() {
        return mActivityImage;
    }

    public void setmActivityImage(int mActivityImage) {

    }

    public String getmActivityName() {
        return mActivityName;
    }

    public void setmActivityName(String mActivityName) {
        this.mActivityName = mActivityName;
    }
}
