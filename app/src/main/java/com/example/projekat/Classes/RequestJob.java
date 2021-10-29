package com.example.projekat.Classes;

import java.util.ArrayList;
import java.util.List;

public class RequestJob {

    public String user;
    public String jobKey;

    public String getUser() {
        return user;
    }

    public String key;

    public void setUser(String user) {
        this.user = user;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RequestJob(){

    }

    public RequestJob(String jobKey,String user){
        this.user = user;
        this.jobKey = jobKey;
    }


}
