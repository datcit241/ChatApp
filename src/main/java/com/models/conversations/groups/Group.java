package com.models.conversations.groups;

import com.models.conversations.Conversation;
import com.models.users.User;

import java.util.*;

public class Group extends Conversation {
    protected Map<String, String> participantsToTheirInviters;
    protected List<JoinRequest> joinRequests;


    public Group() {
        super();
        this.joinRequests = new ArrayList<>();
        this.participantsToTheirInviters = new HashMap<>();
    }

    public Group(List<User> participants) {
        super();
        this.participants = participants;
        this.participantsToTheirInviters = new HashMap<>();
    }

    public Group(User creator, List<User> participants) {
        this(participants);

        participants.forEach(participant -> {
            this.participantsToTheirInviters.put(participant.getUsername(), creator.getUsername());
        });
    }

    public void addUser(User inviter, User candidate) {
        this.participantsToTheirInviters.put(inviter.getUsername(), candidate.getUsername());
        participants.add(candidate);
    }

    public void removeUser(User candidate) {
        participants.remove(candidate);
    }

    public void addJoinRequest(JoinRequest joinRequest) {
        this.joinRequests.add(joinRequest);
    }

    public void removeJoinRequest(JoinRequest joinRequest) {
        this.joinRequests.remove(joinRequest);
    }

}
