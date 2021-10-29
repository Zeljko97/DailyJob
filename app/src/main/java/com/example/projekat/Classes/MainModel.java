package com.example.projekat.Classes;

public class MainModel {


    String jobName,date,description, category,key;
    double latitude;
    double longitude;

    public MainModel(){}



    public MainModel(String jobName, String date, String description, String category, String key, double latitude, double longitude) {
        this.jobName = jobName;
        this.date = date;
        this.description = description;
        this.category = category;
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getJobName() {
        return jobName;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getKey() {return key;}
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setKey(String key){ this.key = key;
    }



    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
