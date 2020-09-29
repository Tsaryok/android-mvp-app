package com.example.walker.model;

public class User {

    private String name;

    private long birthday;

    private String gender;

    public User() {
    }

    public User(String name, long birthday,
                String gender) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public long getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }
}