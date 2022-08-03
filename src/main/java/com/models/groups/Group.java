package com.models.groups;

import com.models.users.User;

import java.util.*;

public class Group {
    private String id;
    private String name;
    private User creator;
    private List<User> members;

    protected Group(String id, User creator, List<User> members) {
        this.creator = creator;
        this.members = members;
    }

    public boolean hasMember(User member) {
        return this.members.contains(member);
    }

    public void addMember(User member) {
        this.members.add(member);
    }

    public void removeMember(User member) {
        members.remove(member);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getCreator() {
        return creator;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<User> getMembers() {
        return this.members;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
