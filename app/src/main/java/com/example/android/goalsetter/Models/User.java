package com.example.android.goalsetter.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String emailAddress;
    @SerializedName("phone_number")
    private String contact;
    @SerializedName("account_type")
    private String account_type;
    @SerializedName("password")
    private String password;
    @SerializedName("password_confirmation")
    private String confirmPassword;

    public User() {

    }

    public User(String name, String contact, String emailAddress, String password, String confirmPassword, String account_type) {
        this.name = name;
        this.contact = contact;
        this.emailAddress = emailAddress;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.account_type = account_type;
    }

    protected User(Parcel in) {
        name = in.readString();
        emailAddress = in.readString();
        contact = in.readString();
        password = in.readString();
        confirmPassword = in.readString();
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

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(emailAddress);
        parcel.writeString(contact);
        parcel.writeString(password);
        parcel.writeString(confirmPassword);
    }
}

