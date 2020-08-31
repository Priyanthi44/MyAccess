package com.appzupp.myaccess.model;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Activity {
    private String activity_id;
    private String activity_name;
    private int activity_image;
    private String instructions;
    private @ServerTimestamp
    Date timestamp;
    private boolean activity_status;
    private String user;

    public Activity(String id, String activity_name, int activity_image, Date timestamp, String instructions, boolean activity_status, String user){
        this.activity_name=activity_name;
        this.activity_image = activity_image;
        this.timestamp=timestamp;
        this.instructions=instructions;
        this.activity_status=activity_status;
        this.user=user;
        this.activity_id=id;
    }

    public Activity(){

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setActivity_image(int activity_image) {
        this.activity_image = activity_image;
    }

    public int getActivity_image() {
        return activity_image;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_id() {
        return  this.activity_id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getActivity_name(){
        return this.activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isActivity_status() {
        return activity_status;
    }

    public void setActivity_status(boolean activity_status) {
        this.activity_status = activity_status;
    }
}
