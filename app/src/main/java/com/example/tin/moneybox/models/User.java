package com.example.tin.moneybox.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tin on 17/06/2018.
 */

public class User implements Parcelable {

    private final String userFirstName; // // Needed for when the arrival time is clicked on to show the line it's connected to in DetailActivity
    private final String userLastName;
    private final String sessionBearerToken; // Needed to find the next three trains and their arrival times

    /* Constructor */
    public User(String userFirstName, String userLastName, String sessionBearerToken) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.sessionBearerToken = sessionBearerToken;
    }

    protected User(Parcel in) {
        userFirstName = in.readString();
        userLastName = in.readString();
        sessionBearerToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userFirstName);
        parcel.writeString(userLastName);
        parcel.writeString(sessionBearerToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getSessionBearerToken() {
        return sessionBearerToken;
    }
}
