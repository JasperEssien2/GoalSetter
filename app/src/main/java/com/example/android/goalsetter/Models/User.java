package com.example.android.goalsetter.Models;

public class User {
    private String name;
    private String contact;
    private String emailAddress;
    private String password;
    private ACCOUNT_TYPE account_type;

    public User(String name, String contact, String emailAddress, String password, ACCOUNT_TYPE account_type) {
        this.name = name;
        this.contact = contact;
        this.emailAddress = emailAddress;
        this.password = password;
        this.account_type = account_type;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ACCOUNT_TYPE getAccount_type() {
        return account_type;
    }

    public void setAccount_type(ACCOUNT_TYPE account_type) {
        this.account_type = account_type;
    }
}

enum ACCOUNT_TYPE {
    PERSONAL, COMPANY
}