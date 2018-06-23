package com.example.tin.moneybox;

import com.example.tin.moneybox.serverConnection.response.ProductResponse;

import java.util.ArrayList;


public interface DetailContract {

    interface DetailScreen {

        void populateDetailView(int moneybox, String friendlyName);

        void updateMoneyBox(int moneybox);
    }

    interface DetailPresenter {

        void prepareArrayListData(ArrayList<ProductResponse.ProductModel> mProducts, int position);

        void depositMoney(DetailActivity context);

    }
}
