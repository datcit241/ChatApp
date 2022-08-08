package com.models.groups;

import com.enums.GroupMemberRole;
import com.models.users.User;

import java.util.*;

public abstract class Group {
    private String id;
    private String name;
    private User creator;
    private List<Member> members;

    protected Group(String id, User creator, List<User> users) {
        this.members = new ArrayList<>();

        Member admin = new Member(creator, GroupMemberRole.Admin);
        this.members.add(admin);

        users.forEach(user -> {
            Member member = new Member(user, GroupMemberRole.Member);
            this.members.add(member);
        });
    }

    private static class Member {
        private GroupMemberRole role;
        private User user;

        public Member(User user, GroupMemberRole role) {
            this.user = user;
            this.role = role;
        }

        public Member(User user) {
            this.role = GroupMemberRole.Member;
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public GroupMemberRole getRole() {
            return role;
        }

        public void setRole(GroupMemberRole role) {
            this.role = role;
        }

    }

    private Member getMember(User user) {
        Member member = members.stream().filter(anyMember -> anyMember.getUser().equals(user)).findFirst().orElse(null);
        return member;
    }

    public boolean hasMember(User user) {
        Member member = this.getMember(user);
        return member != null;
    }

    public void addMember(User user) {
        Member member = new Member(user);
        members.add(member);
    }

    public void removeMember(User user) {
        Member member = this.getMember(user);
        members.remove(member);
    }

    public void setRole(User user, GroupMemberRole role) {
        Member member = this.getMember(user);
        member.setRole(role);
    }

    public GroupMemberRole getRole(User user) {
        Member member = this.getMember(user);
        return member.getRole();
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

    public List<User> getMembers() {
        return members
                .stream()
                .map(Member::getUser)
                .toList();
    }

    public int getGroupSize() {
        return this.members.size();
    }

    public List<User> getAdmins() {
        return members
                .stream()
                .filter(member -> member.getRole() == GroupMemberRole.Admin)
                .map(Member::getUser)
                .toList();
    }

}
