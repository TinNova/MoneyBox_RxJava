package com.example.tin.moneybox;

import android.util.Log;

import com.example.tin.moneybox.serverConnection.RestService;
import com.example.tin.moneybox.serverConnection.body.PaymentBody;
import com.example.tin.moneybox.serverConnection.response.OneOffPaymentResponse;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;
import com.example.tin.moneybox.utils.Const;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DetailPresenter implements DetailContract.DetailPresenter {

    private static final String TAG = DetailPresenter.class.getSimpleName();

    private DetailContract.DetailScreen detailScreen;

    DetailPresenter(DetailContract.DetailScreen screen) {
        this.detailScreen = screen;
    }

    int investorProductId;

    @Override
    public void prepareArrayListData(ArrayList<ProductResponse.ProductModel> mProducts, int position) {

        ProductResponse.ProductModel product = mProducts.get(position);

        int moneybox = product.getMoneybox();
        String productFriendlyName = product.Product.FriendlyName;
        investorProductId = product.getInvestorProductId();

        detailScreen.populateDetailView(moneybox, productFriendlyName);
    }

    @Override
    public void depositMoney(DetailActivity context) {

        /* RxJava of Retrofit Method for login, this logs user in */
        RestService.getInstance(context)
                .payment(new PaymentBody(Const.PAYMENT, investorProductId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OneOffPaymentResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(OneOffPaymentResponse oneOffPaymentResponse) {

                        detailScreen.updateMoneyBox(oneOffPaymentResponse.getMoneybox());
                    }

                    @Override
                    public void onError(Throwable e) {

                        // Called when we have an error in the response
                        // Here you can place a Toast message "Incorrect password" or "No internet data"
                        Log.e(TAG, "error = " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
