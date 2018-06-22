package com.example.tin.moneybox.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tin.moneybox.models.Product;
import com.example.tin.moneybox.models.User;
import com.example.tin.moneybox.utils.ProductJsonUtils;
import com.example.tin.moneybox.utils.UserJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.tin.moneybox.utils.OneOfPaymentUtils.parseOneOffPaymentJson;


public class NetworkConnection {

    private static final String TAG = NetworkConnection.class.getSimpleName();

    private ArrayList<User> mUser = new ArrayList<>();
    private ArrayList<Product> mProduct = new ArrayList<>();

    /* Header Keys */
    private static final String APP_ID_KEY = "AppId";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String APP_VERSION_KEY = "appVersion";
    private static final String API_VERSION_KEY = "apiVersion";
    private static final String AUTHORIZATION_KEY = "Authorization";
    /* Header Values */
    private static final String APP_ID_VALUE = "3a97b932a9d449c981b595";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String APP_VERSION_VALUE = "4.11.0";
    private static final String API_VERSION_VALUE = "3.0.0";
    private String BEARER_TOKEN;

    /* Login Credential Keys */
    private static final String EMAIL_KEY = "Email";
    private static final String PASSWORD_KEY = "Password";
    private static final String IDFA_KEY = "Idfa";
    /* Login Credential Values */
    public static final String IDFA_VALUE = "the idfa of the ios device";

    public static String EMAIL_FOR_TESTING = "test+env12@test.com";
    public static String PASSWORD_FOR_TESTING = "test";

    /* Keys for MoneyBox Deposit */
    private static final String INVESTOR_PRODUCT_ID = "InvestorProductId";
    private static final String AMOUNT_TO_DEPOSIT = "Amount";
    private static final int DEPOSIT_AMOUNT = 10;

    private static NetworkConnection instance = null;

    // Required for Volley API
    private final RequestQueue mRequestQueue;

    private NetworkConnection(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized NetworkConnection getInstance(Context context) {
        if (null == instance) {
            instance = new NetworkConnection(context);
        }
        return instance;
    }

    public void getLoginResponseFromHttpUrl(String url, final String email, final String pass, final NetworkListener.LoginListener listener) {

        Log.d(TAG, "email & password 2: " + email + ", " + pass);

        /* Handler for the JSON response when server returns ok */
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String loginResponse) {

                /** Here we handle the response*/
                Log.d(TAG, "onResponse loginResponse: " + loginResponse);

                mUser = UserJsonUtils.parseUserJson(loginResponse);

                listener.getResponse(mUser);

            }
        };

        /* Handler for when the server returns an error response */
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse login() " + error);
                error.printStackTrace();
            }
        };

        /* This is the body of the Request */
        StringRequest request = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(APP_ID_KEY, APP_ID_VALUE);
                headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
                headers.put(APP_VERSION_KEY, APP_VERSION_VALUE);
                headers.put(API_VERSION_KEY, API_VERSION_VALUE);
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return CONTENT_TYPE_VALUE;
            }

            /*
             * Passing in Email, Pass and Idfa via getBody as JsonObjects instead of via getParams
             * because a Json is required
             */
            @Override
            public byte[] getBody() throws AuthFailureError {

                //TODO API CALL TO LOGIN
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EMAIL_KEY, EMAIL_FOR_TESTING);
                    jsonObject.put(PASSWORD_KEY, PASSWORD_FOR_TESTING);
                    jsonObject.put(IDFA_KEY, IDFA_VALUE);

                } catch (JSONException exc) {
                    exc.printStackTrace();
                }
                return jsonObject.toString().getBytes();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(EMAIL_KEY, EMAIL_FOR_TESTING);
                params.put(PASSWORD_KEY, PASSWORD_FOR_TESTING);
                params.put(IDFA_KEY, IDFA_VALUE);
                return params;
            }
        };

        mRequestQueue.add(request);
    }

    public void getThisWeekResponseFromHttpUrl(String url, final ArrayList<User> user, final NetworkListener.ThisWeekListener listener) {

        //TODO: Save Bearer In SavedPreferences Instead, this will ensure it's easier to access??
        BEARER_TOKEN = "Bearer " + user.get(0).getSessionBearerToken();

        Log.d(TAG, "BearerToken: " + BEARER_TOKEN);

        /* Handler for the JSON response when server returns ok */
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            /* If response is successful */
            @Override
            public void onResponse(String response) {

                /** Here we handle the response*/
                mProduct = ProductJsonUtils.parseProductJson(response);

                Log.d(TAG, "thisWeek Response: " + mProduct);

                listener.getResponse(mProduct);

            }
        };

        /* Handler for when the server returns an error response */
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse login() " + error);
                error.printStackTrace();
            }
        };

        /* This is the body of the Request */
        StringRequest request = new StringRequest(Request.Method.GET, url, responseListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(APP_ID_KEY, APP_ID_VALUE);
                headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
                headers.put(APP_VERSION_KEY, APP_VERSION_VALUE);
                headers.put(API_VERSION_KEY, API_VERSION_VALUE);
                headers.put(AUTHORIZATION_KEY, BEARER_TOKEN);
                return headers;
            }
        };

        mRequestQueue.add(request);
    }

    public void getLogOutResponseFromHttpUrl(String url, final NetworkListener.LogoutListener listener) {

        /* Handler for the JSON response when server returns ok */
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            /* If response is successful */
            @Override
            public void onResponse(String response) {

                /** Here we handle the response*/
                String logout = "successful logout";
                listener.getResponse(logout);
            }
        };

        /* Handler for when the server returns an error response */
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse login() " + error);
                error.printStackTrace();
            }
        };

        /* This is the body of the Request */
        StringRequest request = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(APP_ID_KEY, APP_ID_VALUE);
                headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
                headers.put(APP_VERSION_KEY, APP_VERSION_VALUE);
                headers.put(API_VERSION_KEY, API_VERSION_VALUE);
                headers.put(AUTHORIZATION_KEY, BEARER_TOKEN);
                return headers;
            }
        };

        mRequestQueue.add(request);
    }

    public void getOneOffPaymentsResponseFromHttpUrl(String url, final int investorProductId, final NetworkListener.OneOffPaymentListener listener) {

        /* Handler for the JSON response when server returns ok */
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            /* If response is successful */
            @Override
            public void onResponse(String response) {

                /** Here we handle the response*/
                int amountInMoneybox = parseOneOffPaymentJson(response);

                Log.d(TAG, "Amount Deposited: " + amountInMoneybox);

                listener.getResponse(amountInMoneybox);
            }
        };

        /* Handler for when the server returns an error response */
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse login() " + error);
                error.printStackTrace();
            }
        };

        /* This is the body of the Request */
        StringRequest request = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(APP_ID_KEY, APP_ID_VALUE);
                headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
                headers.put(APP_VERSION_KEY, APP_VERSION_VALUE);
                headers.put(API_VERSION_KEY, API_VERSION_VALUE);
                headers.put(AUTHORIZATION_KEY, BEARER_TOKEN);
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return CONTENT_TYPE_VALUE;
            }

            /*
             * Passing in deposit amount and InvestorProductID as JsonObjects instead of via getParams
             * because a Json is required
             */
            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(AMOUNT_TO_DEPOSIT, DEPOSIT_AMOUNT);
                    jsonObject.put(INVESTOR_PRODUCT_ID, investorProductId);

                } catch (JSONException exc) {
                    exc.printStackTrace();
                }
                return jsonObject.toString().getBytes();
            }
        };

        mRequestQueue.add(request);
    }
}
