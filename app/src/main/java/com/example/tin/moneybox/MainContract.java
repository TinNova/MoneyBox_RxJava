package com.example.tin.moneybox;

import android.content.Context;

import com.example.tin.moneybox.serverConnection.response.ProductResponse;

import java.util.ArrayList;


interface MainContract {

    interface MainScreen {

        void showProducts(ArrayList<ProductResponse.ProductModel> products);

        void logout();

        Context provideContext();

        void showLoading();

        void hideLoading();

    }

    interface MainPresenter {

        void getThisWeekResponse(MainActivity mainActivity);

        void startLogOut(MainActivity mainActivity);

        void hideLoading();

    }
}
