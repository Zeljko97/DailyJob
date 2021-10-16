package com.example.projekat;

public class RequestJob {

    public String user;
    public String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RequestJob(){

    }

    public RequestJob(String key,String user){
        this.key = key;
        this.user = user;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
