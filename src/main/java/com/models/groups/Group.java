package com.models.groups;

import com.models.users.User;

import java.util.*;

public class Group {
    private String id;
    private String name;
    private User creator;
    private List<User> participants;

    public Group(String id, User creator, List<User> participants) {
        this.creator = creator;
        this.participants = participants;
    }

    public boolean hasParticipant(User participant) {
        return this.participants.contains(participant);
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(User participant) {
        participants.remove(participant);
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

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<User> getParticipants() {
        return this.participants;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
