package com.example.tin.moneybox.utils;

import android.util.Log;

import com.example.tin.moneybox.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class UserJsonUtils {

    private static final String TAG = UserJsonUtils.class.getSimpleName();

    /* Values */
    private static final String USER_FIRST_NAME = "FirstName";
    private static final String USER_LAST_NAME = "LastName";
    private static final String SESSION_BEARER_TOKEN = "BearerToken";
    /* JsonObjects */
    private static final String JSON_OBJECT_USER = "User";
    private static final String JSON_OBJECT_SESSION = "Session";

    public static ArrayList<User> parseUserJson(String response) {

        ArrayList<User> mUser = new ArrayList<>();

        try {

            /* Define the entire response as a JSON Object */
            JSONObject moneyBoxJsonObject = new JSONObject(response);

            /* Define the "User" JsonObject as a JSONObject */
            JSONObject userJsonObject = moneyBoxJsonObject.getJSONObject(JSON_OBJECT_USER);

            String userFirstName = userJsonObject.getString(USER_FIRST_NAME);
            String userLastName = userJsonObject.getString(USER_LAST_NAME);

            /* Define the "Session" JsonObject as a JSONObject */
            JSONObject sessionJsonObject = moneyBoxJsonObject.getJSONObject(JSON_OBJECT_SESSION);

            String sessionBearerToken = sessionJsonObject.getString(SESSION_BEARER_TOKEN);

            User user = new User(
                    userFirstName,
                    userLastName,
                    sessionBearerToken
            );

            mUser.add(user);
            Log.d(TAG, "User List: " + user);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mUser;

    }

}
