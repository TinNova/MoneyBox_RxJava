package com.example.tin.moneybox;

import android.content.Context;

import com.example.tin.moneybox.serverConnection.response.ProductResponse;

import java.util.ArrayList;

/**
 * Created by Tin on 17/06/2018.
 */

public interface MainContract {

    interface MainScreen {

        void showProducts(ArrayList<ProductResponse.ProductModel> products);

        void logout();

        Context provideContext();
    }

    interface MainPresenter {

        void getThisWeekResponse(MainActivity mainActivity);

        void startLogOut(MainActivity mainActivity);
    }
}
