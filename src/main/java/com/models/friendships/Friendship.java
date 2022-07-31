package com.models.friendships;

import com.models.users.User;

import java.util.HashMap;
import java.util.Map;

public class Friendship {
    private User firstPerson;
    private User secondPerson;
    private String friendship;

    private Map<User, String> aliases;

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

        this.aliases = new HashMap<>();
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

    public void setAlias(User assignee, String alias) {
        this.aliases.put(assignee, alias);
    }

    public String getFriendName(User user) {
        User friend = this.getFriend(user);

        String alias = this.aliases.get(friend);

        if (alias == null) {
            return friend.getFullName();
        }

        return alias;
    }

    @Override
    public int hashCode() {
        return friendship.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Friendship)) {
            return false;
        }

        return this.hashCode() == ((Friendship) obj).hashCode();
    }

    @Override
    public String toString() {
        return friendship;
    }
}
