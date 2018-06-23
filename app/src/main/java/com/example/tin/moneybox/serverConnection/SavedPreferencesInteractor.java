package com.example.tin.moneybox.serverConnection;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dmytrovodnik on 1/23/18.
 */

/**
 * SharedPreferences that saves the token
 */
public class SavedPreferencesInteractor {


    private static final String TOKEN = "BearerToken";
    private SharedPreferences sharedPreferences;

    public SavedPreferencesInteractor(Context application) {

        sharedPreferences = application.getSharedPreferences("ME_PREFS", Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {

        sharedPreferences.edit().putString(TOKEN, token).apply();
    }

    public String getToken() {

        return sharedPreferences.getString(TOKEN, "");
    }

}
