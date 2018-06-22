package com.example.tin.moneybox;

import android.util.Log;

import com.example.tin.moneybox.models.Product;
import com.example.tin.moneybox.network.NetworkConnection;
import com.example.tin.moneybox.network.NetworkListener;
import com.example.tin.moneybox.utils.UrlUtils;

import java.util.ArrayList;


public class DetailPresenter implements DetailContract.DetailPresenter {

    private static final String TAG = DetailPresenter.class.getSimpleName();

    private DetailContract.DetailScreen detailScreen;

    DetailPresenter(DetailContract.DetailScreen screen) {
        this.detailScreen = screen;
    }

    int investorProductId;

    @Override
    public void prepareArrayListData(ArrayList<Product> mProducts, int position) {

        Product product = mProducts.get(position);

        int moneybox = product.getMoneybox();
        String productFriendlyName = product.getFriendlyName();
        investorProductId = product.getInvestorProductId();

        detailScreen.populateDetailView(moneybox, productFriendlyName);

    }

    @Override
    public void depositMoney(DetailActivity context) {

        String oneOffPaymentsUrl = UrlUtils.getOneOffPaymentsUrl();

        /* Use the String URL "loginUrl" to request the JSON from the server and parse it */
        NetworkConnection.getInstance(context).getOneOffPaymentsResponseFromHttpUrl(oneOffPaymentsUrl, investorProductId, new NetworkListener.OneOffPaymentListener() {

            @Override
            public void getResponse(int amountInMoneybox) {

                detailScreen.updateMoneyBox(amountInMoneybox);
            }
        });

    }
}
