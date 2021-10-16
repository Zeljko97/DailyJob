package com.example.projekat;

public class JobDealModel {

    String jobName;
    String description;
    double latitude, longitude;
    String employer;

    public JobDealModel(){}


    public JobDealModel(String jobName, String description, String employer, double latitude, double longitude) {
        this.jobName = jobName;
        this.description = description;
        this.employer = employer;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

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



}
