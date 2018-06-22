package com.example.tin.moneybox.utils;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tin on 16/06/2018.
 */

public class UrlUtils {

    private static final String TAG = UrlUtils.class.getSimpleName();

    /* Base url for the URL */
    private static final String BASE_MONEYBOX_URL = "https://api-test00.test.com";

    /* Paths */
    private static final String USERS = "users";
    private static final String LOGIN = "login";
    private static final String LOGOUT = "logout";
    private static final String INVESTOR_PRODUCT = "investorproduct";
    private static final String THIS_WEEK = "thisweek";
    private static final String ONE_OFF_PAYMENTS = "oneoffpayments";

    public static String getLoginUrl() {

        return buildLoginUrl();
    }

    public static String getLogoutUrl() {

        return buildLogOutUrl();
    }

    public static String getThisWeekUrl() {

        return buildThisWeekUrl();
    }

    public static String getOneOffPaymentsUrl() {

        return buildOneOffPaymentsUrl();
    }

    private static String buildLoginUrl() {
        Uri moneyboxQueryUri = Uri.parse(BASE_MONEYBOX_URL).buildUpon()
                .appendPath(USERS)
                .appendPath(LOGIN)
                .build();

        try {

            URL url = new URL(moneyboxQueryUri.toString());
            Log.v(TAG, "moneyboxQueryUrl: " + url);
            return convertUrlToString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String buildLogOutUrl() {
        Uri moneyboxQueryUri = Uri.parse(BASE_MONEYBOX_URL).buildUpon()
                .appendPath(USERS)
                .appendPath(LOGOUT)
                .build();

        try {

            URL url = new URL(moneyboxQueryUri.toString());
            Log.v(TAG, "moneyboxQueryUrl: " + url);
            return convertUrlToString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String buildThisWeekUrl() {
        Uri moneyboxQueryUri = Uri.parse(BASE_MONEYBOX_URL).buildUpon()
                .appendPath(INVESTOR_PRODUCT)
                .appendPath(THIS_WEEK)
                .build();

        try {

            URL url = new URL(moneyboxQueryUri.toString());
            Log.v(TAG, "moneyboxQueryUrl: " + url);
            return convertUrlToString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String buildOneOffPaymentsUrl() {
        Uri moneyboxQueryUri = Uri.parse(BASE_MONEYBOX_URL).buildUpon()
                .appendPath(ONE_OFF_PAYMENTS)
                .build();

        try {

            URL url = new URL(moneyboxQueryUri.toString());
            Log.v(TAG, "moneyboxQueryUrl: " + url);
            return convertUrlToString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertUrlToString(URL url) throws MalformedURLException {

        return url.toString();
    }

}
