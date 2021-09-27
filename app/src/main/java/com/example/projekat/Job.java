package com.example.projekat;

public class Job {

    public String jobName;
    public String category;
    public String description;
    public double longitude;
    public double latitude;
    public String date;
    public String key; //atribut key ce biti dodeljen svakom smestenom objektu od strane Firebase baze, zbog toga ga nije potrebno dodavati u bazu podataka prilikom snimanja


    public Job(){

    }

    public Job(String jobName, String category, String description,  String date){
        this.jobName = jobName;
        this.category = category;
        this.description = description;
        this.longitude = 0;
        this.latitude = 0;
        this.date = date;


    }
    public Job(String jobName, String category, String description, double longitude, double latitude, String date){
        this.jobName = jobName;
        this.category = category;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;

    }

    public String getJobName(){
        return this.jobName;
    }
    public String getCategory(){
        return category;
    }
    public String getDescription(){
        return description;
    }
    public double getLongitude(){
        return longitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public String getDate(){
        return date;
    }

}
