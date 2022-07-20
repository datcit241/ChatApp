package com.models.users;

import com.models.conversations.groups.Group;
import com.models.enums.Gender;
import com.models.files.File;
import com.utilities.HashHelper;

import java.util.*;

public class User {
    private String username;
    private String lastName;
    private String firstName;
    private String fullName;
    private Gender gender;
    private String hashedPassword;
    private Date dateOfBirth;

    private List<Group> listOfGroups;
    private List<User> friends;

    public User(String username, String password, String lastName, String firstName, Gender gender, Date dateOfBirth) {
        this.username = username;
        this.hashedPassword = HashHelper.hash(password);
        this.lastName = lastName;
        this.firstName = firstName;
        this.fullName = lastName + firstName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.friends = new ArrayList<>();
    }

    public void setPassword(String password) {
        this.hashedPassword = HashHelper.hash(password);
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

    public void joinGroup(Group group) {
        this.listOfGroups.add(group);
    }
    public List<Group> getListOfGroups() {
        return listOfGroups;
    }

    public List<User> getFriends() {
        return friends;
    }
}
