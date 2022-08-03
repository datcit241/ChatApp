package com.models.users;

import com.enums.Gender;
import com.utilities.HashHelper;

import java.time.LocalDate;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private Gender gender;
    private String hashedPassword;
    private LocalDate dateOfBirth;

    public User(String username, String password, String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        this.username = username;
        this.hashedPassword = HashHelper.hash(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassword(String password) {
        this.hashedPassword = HashHelper.hash(password);
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        return this.hashCode() == ((User) obj).hashCode();
    }
}
