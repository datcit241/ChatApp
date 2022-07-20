package com.services;

import com.models.users.FriendRequest;
import com.models.users.User;

public class FriendRequestService {

    public static void sendRequest(User sender, User receiver) {
        FriendRequest request = new FriendRequest(sender, receiver);
    }

}
