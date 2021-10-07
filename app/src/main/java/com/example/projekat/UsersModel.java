package com.example.projekat;

public class UsersModel {

    String email,name,phoneNumber,profession;

    public UsersModel(){

    }
    public UsersModel(String email, String name, String phoneNumber,String profession){
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
