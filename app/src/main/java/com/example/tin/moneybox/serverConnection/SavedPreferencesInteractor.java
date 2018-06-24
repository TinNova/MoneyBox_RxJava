package com.example.tin.moneybox.serverConnection;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences that saves the token
 */
public class SavedPreferencesInteractor {


    private static final String TOKEN = "BearerToken";
    private final SharedPreferences sharedPreferences;

    private static SavedPreferencesInteractor INSTANCE;
    private static Context CONTEXT;
    private static final Object lock = new Object();

    public static void context(Context context) {
        CONTEXT = context;
    }

    public SavedPreferencesInteractor(Context application) {

        sharedPreferences = application.getSharedPreferences("ME_PREFS", Context.MODE_PRIVATE);
    }

    public static SavedPreferencesInteractor getInstance() {
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    INSTANCE = new SavedPreferencesInteractor(CONTEXT);
                }
            }
        }
        return INSTANCE;
    }

    public void saveToken(String token) {

        sharedPreferences.edit().putString(TOKEN, token).apply();
    }

    public String getToken() {

        return sharedPreferences.getString(TOKEN, "");
    }

}
