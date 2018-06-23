package com.example.tin.moneybox;

import android.util.Log;
import android.widget.Toast;

import com.example.tin.moneybox.models.User;
import com.example.tin.moneybox.network.NetworkConnection;
import com.example.tin.moneybox.network.NetworkListener;
import com.example.tin.moneybox.serverConnection.RestService;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;
import com.example.tin.moneybox.utils.UrlUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class MainPresenter implements MainContract.MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private MainContract.MainScreen mainScreen;

    MainPresenter(MainContract.MainScreen screen) {
        this.mainScreen = screen;
    }

    @Override
    public void getThisWeekResponse(MainActivity context) {

        String thisWeekUrl = UrlUtils.getThisWeekUrl();

                /* This is getting the Products list, should be moved to MainPresenter
            This is called on a successful or failed endpoint connection */
        RestService.getInstance(context)
                .getProducts()
                // Above we are:
                // 1. Calling .getProducts(), this is an observable
                // 2. No Params or body is required for this endpoint, so the bracket is empty in ".getProducts():
                // 3. RestService is calling the method .getProducts from the RestService Class the data retrieved is an observable
                // 4. In the response we don't receive the Json, instead it is already parsed by Retrofit

                .observeOn(AndroidSchedulers.mainThread()) // On which thread we want to see result
                // Above we are:
                // 1. Specifying on which thread we want to see the response, here we are stating we want it on the mainThread()
                // 2. Here we can specify what to do once we have a successful response i.e launch the next activity

                .subscribe(new Observer<ProductResponse>() {
                    // Above we are:
                    // 1. .subscribe this is where we can interact with the result (error or successful)
                    // 2. Here we can specify what to do once we have a successful response i.e launch the next activity

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProductResponse productResponse) {

                        Log.d(TAG, "successful load of products " + productResponse.getProducts());

                        mainScreen.showProducts(productResponse.getProducts());

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        Log.e(TAG, "error while load products " + Log.getStackTraceString(throwable));
                    }

                    @Override
                    public void onComplete() {

                    }

                    /** Lambda Code which can replace the @Override Methods, it's the same code, but it looks smaller and easier to read */
//               .subscribe(product -> { //Lambda code, it is the same as the above @overide onSubscribe ect...
//
//                    Log.d(TAG, "successul load of products " + product);
//                }, throwable -> {
//                    Log.e(TAG, "error while load products " + Log.getStackTraceString(throwable));
                });


        /** Old Volley Code*/
//        /* Use the String URL "loginUrl" to request the JSON from the server and parse it */
//        NetworkConnection.getInstance(context).getThisWeekResponseFromHttpUrl(thisWeekUrl, user, new NetworkListener.ThisWeekListener() {
//
//            @Override
//            public void getResponse(ArrayList<Product> products) {
//
//                Log.d(TAG, "thisWeek Products ArrayList: " + products);
//                Log.d(TAG, "thisWeek Products ArrayList: " + products);
//
//                mainScreen.showProducts(products);
//            }
//        });
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
