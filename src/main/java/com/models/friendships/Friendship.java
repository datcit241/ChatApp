package com.models.friendships;

import com.models.users.User;

public class Friendship {
    private User firstPerson;
    private User secondPerson;
    private String friendship;

    public Friendship(User firstPerson, User secondPerson){
        int comparison = firstPerson.getUsername().compareTo(secondPerson.getUsername());

        if (comparison < 0) {
            this.firstPerson = firstPerson;
            this.secondPerson = secondPerson;
        } else {
            this.firstPerson = secondPerson;
            this.secondPerson = firstPerson;
        }

        this.friendship = firstPerson.getUsername() + secondPerson.getUsername();
    }

    public boolean isRelatedTo(User person) {
        return person.equals(firstPerson) || person.equals(secondPerson);
    }

    public boolean isRelatedTo(User person1, User person2) {
        return this.isRelatedTo(person1) && this.isRelatedTo(person2);
    }

    public User getFriend(User user) {
        if (!this.isRelatedTo(user)) {
            return null;
        }

        if (user.equals(this.firstPerson)) {
            return this.secondPerson;
        }

        return this.firstPerson;
    }

    @Override
    public int hashCode() {
        return friendship.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == ((Friendship) obj).hashCode();
    }

    @Override
    public String toString() {
        return friendship;
    }
}
