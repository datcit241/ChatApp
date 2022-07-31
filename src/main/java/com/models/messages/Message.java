package com.models.messages;

import com.models.enums.RelationToAMessage;
import com.models.files.File;
import com.models.users.User;

import javax.servlet.http.Part;
import java.time.*;
import java.util.Comparator;

public class Message {
    public static Comparator messageByRecentnessComparator;

    static {
        messageByRecentnessComparator = (o1, o2) -> -((Message) o1).getSentAt().compareTo(((Message) o2).getSentAt());
    }

    private LocalDateTime sentAt;
    private User sender;
    private Object receiver;
    private String textContent;
    private File file;

    public Message(User sender, Object receiver, String text, File file) {
        this.sender = sender;
        this.receiver = receiver;
        this.textContent = text;
        this.sentAt = LocalDateTime.now();
        this.file = file;
    }

    private File createFile(Part out) {
        return new File(null, null, null, null);
    }

    public RelationToAMessage getRelation(Object conversationEntity) {
        boolean isSender = conversationEntity.equals(sender);
        boolean isReceiver = conversationEntity.equals(receiver);

        if (!isSender && !isReceiver) {
            return RelationToAMessage.NotRelated;
        }

        if (isSender) {
            return RelationToAMessage.Sender;
        }

        return RelationToAMessage.Receiver;
    }

    public boolean isRelatedTo(Object conversationEntity) {
        if (this.getRelation(conversationEntity) == RelationToAMessage.NotRelated) {
            return false;
        }

        return true;
    }

    public boolean isRelatedTo(Object conversationEntity1, Object conversationEntity2) {
        return this.isRelatedTo(conversationEntity1) && this.isRelatedTo(conversationEntity2);
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public User getSender() {
        return sender;
    }

    public Object getReceiver() {
        return receiver;
    }

    public String getTextContent() {
        return textContent;
    }

    public File getFile() {
        return file;
    }
}
