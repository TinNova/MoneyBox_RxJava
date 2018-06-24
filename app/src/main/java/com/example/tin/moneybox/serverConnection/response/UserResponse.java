package com.example.tin.moneybox.serverConnection.response;

import android.os.Parcel;
import android.os.Parcelable;

public class UserResponse implements Parcelable {

    /* The JsonObjects we want to get data from */
    public Session Session;
    private UserModel User;


    /* Within the Session JsonObject, the values we want to take*/
    public static class Session {

        public String BearerToken;

        @Override
        public String toString() {
            return "Session{" +
                    "BearerToken='" + BearerToken + '\'' +
                    '}';
        }

        public String getBearerToken() {
            return BearerToken;
        }
    }

    /* Within the User JsonObject, the values we want to take*/
    public static class UserModel {

        public String LastName;

        public String FirstName;


        @Override
        public String toString() {
            return "UserModel{" +
                    "LastName='" + LastName + '\'' +
                    ", FirstName='" + FirstName + '\'' +
                    '}';
        }

        public String getLastName() {
            return LastName;
        }

        public String getFirstName() {
            return FirstName;
        }
    }

    public UserResponse.Session getSession() {
        return Session;
    }

    public UserModel getUserModel() {
        return User;
    }




    private UserResponse(Parcel in) {
    }

    public static final Creator<UserResponse> CREATOR = new Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel in) {
            return new UserResponse(in);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };

    @Override
    public String toString() {
        return "UserResponse{" +
                "Session=" + Session +
                ", User=" + User +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
