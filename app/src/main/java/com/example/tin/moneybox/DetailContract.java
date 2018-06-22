package com.example.tin.moneybox;

import com.example.tin.moneybox.models.Product;

import java.util.ArrayList;

/**
 * Created by Tin on 18/06/2018.
 */

public interface DetailContract {

    interface DetailScreen {

        void populateDetailView(int moneybox, String friendlyName);

        void updateMoneyBox(int moneybox);
    }

    interface DetailPresenter {

        void prepareArrayListData(ArrayList<Product> mProducts, int position);

        void depositMoney(DetailActivity context);

    }
}
