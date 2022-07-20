package com.models.conversations.groups;

import com.models.users.User;

import java.time.LocalDateTime;

public class JoinRequest {
    private User inviter;
    private User candidate;
    private LocalDateTime requestedAt;

    public JoinRequest(User inviter, User candidate) {
        this.inviter = inviter;
        this.candidate = candidate;
        this.requestedAt = LocalDateTime.now();
    }

    public User getInviter() {
        return inviter;
    }

    public User getCandidate() {
        return candidate;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }
}
