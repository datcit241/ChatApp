package com.models.users;

import com.models.conversations.groups.Group;
import com.models.enums.Gender;
import com.services.GFG2;

import javax.naming.InvalidNameException;
import java.util.*;

public class User {
    private String username;
    private String lastName;
    private String firstName;
    private String fullName;
    private Gender gender;
    private String hashedPassword;
    private Date dateOfBirth;

    private ArrayList<Group> listOfGroups;

    public User(String username, String password, String lastName, String firstName, Gender gender, Date dateOfBirth) {
        this.username = username;
        this.hashedPassword = GFG2.hash(password);
        this.lastName = lastName;
        this.firstName = firstName;
        this.fullName = lastName + firstName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassword(String password) {
        this.hashedPassword = GFG2.hash(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return hashedPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
