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

    public boolean removeParticipants(User participant) {
        if (!participants.contains(participant)) {
            return false;
        }

        participants.remove(participant);
        return true;
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

    public boolean addParticipant(User participant) {
        if (participants.contains(participant)) {
            return false;
        }

        this.participants.add(participant);
        return true;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
