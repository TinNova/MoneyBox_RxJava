package com.example.tin.moneybox;

import android.content.Context;

import com.example.tin.moneybox.serverConnection.response.UserResponse;


public interface LoginContract {

    interface LoginScreen {

        void launchMainActivity(String firstName, String sessionToken);

        Context provideContext();

        void showLoading();

    }


    interface LoginPresenter {

        void startLogin(Context context, String email, String pass);

    }
}
