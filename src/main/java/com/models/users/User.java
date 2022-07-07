package com.models.users;

import java.util.Date;

public class User {
    private String lastName, firstName, fullName, gender;
    private int password;
    private Date dateOfBirth;

    public User(String lastName, String firstName, String fullName, String password, String gender, Date dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.fullName = fullName;
        this.password = password.hashCode();
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
}
