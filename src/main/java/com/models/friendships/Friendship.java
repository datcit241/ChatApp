package com.models.friendships;

public class Friendship {
    private String firstPersonId;
    private String secondPersonId;
    private String friendship;

    public Friendship(String id1, String id2){
        int comparison = id1.compareTo(id2);

        if (comparison < 0) {
            this.firstPersonId = id1;
            this.secondPersonId = id2;
        } else {
            this.firstPersonId = id2;
            this.secondPersonId = id1;
        }

        this.friendship = firstPersonId + secondPersonId;
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
