package com.models.users;

import com.models.conversations.groups.Group;
import com.services.GFG2;

import java.util.*;

public class User {
    private String lastName;
    private String firstName;
    private String fullName;
    private String gender;
    private String hashedPassword;
    private int id;
    private Date dateOfBirth;

    private ArrayList<Group> listOfGroups;

    public User(String lastName, String firstName, String fullName, String password, String gender, Date dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.fullName = fullName;
        this.hashedPassword = GFG2.hash(password);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.id = this.hashCode();
    }

    public static int hashPassword(String password) {
        return password.hashCode();
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = GFG2.hash(password);
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

    public String getGender() {
        return gender;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public int getId() {
        return id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
