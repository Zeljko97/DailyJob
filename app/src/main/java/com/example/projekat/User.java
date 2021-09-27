package com.example.projekat;

public class User {

    public String name;
    public String email;
    public String phoneNumber;
    public String profession;
    public String profileImageUri;

    public double latitude;
    public double longitude;

    public User(){}

    public User(String name, String email, String phoneNumber, String profession, String profileImageUri)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
        this.profileImageUri = profileImageUri;

        this.latitude = 0;
        this.longitude = 0;
    }


    public String getName()
    {
        return name;
    }
    public String getEmail()
    {
        return email;
    }
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public String getProfession()
    {
        return profession;
    }
    public String getProfileImageUri()
    {
        return  profileImageUri;
    }

}
