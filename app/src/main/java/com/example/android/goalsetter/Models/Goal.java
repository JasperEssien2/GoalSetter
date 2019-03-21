package com.example.android.goalsetter.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Class for Goals
 */
public class Goal implements Parcelable {

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("begin_date")
    private String startTime;
    @SerializedName("due_date")
    private String dueTime;
    @SerializedName("level")
    private String level;
    @SerializedName("id")
    private int id;

    public Goal(String title, String description, String level, String startTime, String dueTime) {

        this.title = title;
        this.description = description;
        this.level = level;
        this.startTime = startTime;
        this.dueTime = dueTime;
    }

    protected Goal(Parcel in) {
        title = in.readString();
        description = in.readString();
        level = in.readString();
        startTime = in.readString();
        dueTime = in.readString();
        id = in.readInt();
    }

    public Goal() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(level);
        parcel.writeString(startTime);
        parcel.writeString(dueTime);
        parcel.writeInt(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
