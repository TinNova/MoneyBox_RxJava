package com.example.tin.moneybox.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tin on 20/06/2018.
 */

public class OneOfPaymentUtils {

    private static final String P_MONEYBOX = "Moneybox";

    public static int parseOneOffPaymentJson(String response) {

        //JSONObject oneOffPaymentJsonObject = null;

        int moneybox = 0;

        try {
            /* Define the response as a JsonObject */
            JSONObject oneOffPaymentJsonObject = new JSONObject(response);

            /* Retrieve the amount deposited */
            moneybox = oneOffPaymentJsonObject.getInt(P_MONEYBOX);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return moneybox;
    }
}
