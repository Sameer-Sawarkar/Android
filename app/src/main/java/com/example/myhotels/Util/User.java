package com.example.myhotels.Util;

import java.util.List;

public class User {
    private List<String> users;
    private List<String> passwords;

    public List<String> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<String> passwords) {
        this.passwords = passwords;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
