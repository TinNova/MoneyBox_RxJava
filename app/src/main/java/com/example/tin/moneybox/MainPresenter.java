package com.example.tin.moneybox;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tin.moneybox.models.Product;
import com.example.tin.moneybox.models.User;
import com.example.tin.moneybox.network.NetworkConnection;
import com.example.tin.moneybox.network.NetworkListener;
import com.example.tin.moneybox.utils.UrlUtils;

import java.util.ArrayList;


public class MainPresenter implements MainContract.MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private MainContract.MainScreen mainScreen;

    MainPresenter(MainContract.MainScreen screen) {
        this.mainScreen = screen;
    }

    @Override
    public void getThisWeekResponse(MainActivity context, ArrayList<User> user) {

        String thisWeekUrl = UrlUtils.getThisWeekUrl();

        /* Use the String URL "loginUrl" to request the JSON from the server and parse it */
        NetworkConnection.getInstance(context).getThisWeekResponseFromHttpUrl(thisWeekUrl, user, new NetworkListener.ThisWeekListener() {

            @Override
            public void getResponse(ArrayList<Product> products) {

                Log.d(TAG, "thisWeek Products ArrayList: " + products);
                Log.d(TAG, "thisWeek Products ArrayList: " + products);

                mainScreen.showProducts(products);
            }
        });
    }

    @Override
    public void startLogOut(MainActivity context) {

        String logoutUrl = UrlUtils.getLogoutUrl();

        //TODO: Implement Logout
        Toast.makeText(context, "logout...", Toast.LENGTH_SHORT).show();

        //TODO: CHECK IF TOKEN HAS EXPIRED BEFORE TRYING TO LOGOUT
        /* Use the String URL "logoutUrl" to request the JSON from the server and parse it */
        NetworkConnection.getInstance(context).getLogOutResponseFromHttpUrl(logoutUrl, new NetworkListener.LogoutListener() {

            @Override
            public void getResponse(String string) {

                Log.v(TAG, "Logout Successful: " + string);

                mainScreen.logout();
            }
        });
    }
}
