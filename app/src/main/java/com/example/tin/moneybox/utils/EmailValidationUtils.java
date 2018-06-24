package com.example.tin.moneybox.utils;

/**
 * Created by Tin on 24/06/2018.
 */

public class EmailValidationUtils {

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
