package com.example.projekat;

import com.example.projekat.Job;

public class JobDeal {

    public String firstUser;
    public String secondUser;
    public String key;
    public String jobName;
    public double latitude;
    public double longitude;
    public String description;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }

    public JobDeal(){}

    public JobDeal(String firstUser, String secondUser){
        this.firstUser = firstUser;
        this.secondUser = secondUser;

    }

   /* public JobDeal(String firstUser, String secondUser, String jobName){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.jobName = jobName;
    }*/
    public JobDeal(String firstUser, String secondUser, String jobName,Double lat, Double lon,String description){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.jobName = jobName;
        this.latitude = lat;
        this.longitude = lon;
        this.description = description;
    }

  /*  public JobDeal(String firstUser, String secondUser,String key){
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.key = key;
    }*/
}
