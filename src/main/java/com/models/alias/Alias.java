package com.models.alias;

import com.models.users.User;

public class Alias {
    private User assigner;
    private User assignee;
    private String alias;

    public Alias(User assigner, User assignee, String alias) {
        this.assigner = assigner;
        this.assignee = assignee;
        this.alias = alias;
    }

    public User getAssigner() {
        return assigner;
    }

    public User getAssignee() {
        return assignee;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Alias)) {
            return false;
        }

        Alias alias = (Alias) obj;
        return alias.getAssignee().equals(this.assignee) && alias.getAssigner().equals(this.assigner);
    }

}
