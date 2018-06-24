package com.example.tin.moneybox;

import android.util.Log;
import android.widget.Toast;

import com.example.tin.moneybox.serverConnection.RestService;
import com.example.tin.moneybox.serverConnection.SavedPreferencesInteractor;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter implements MainContract.MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    SavedPreferencesInteractor savedPrefInteractor;

    private MainContract.MainScreen mainScreen;

    MainPresenter(MainContract.MainScreen screen) {
        this.mainScreen = screen;
        this.savedPrefInteractor = new SavedPreferencesInteractor(screen.provideContext());
    }

    @Override
    public void getThisWeekResponse(MainActivity context) {

        /* This is getting the Products list, should be moved to MainPresenter
           This is called on a successful or failed endpoint connection */
        RestService.getInstance(context)
                .getProducts()
                .subscribeOn(Schedulers.io())
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
                });
    }


    //TODO: During the Logout, HTTP return 200, but the code always goes to onError, why is this? How to fix it?
    @Override
    public void startLogOut(MainActivity context) {

        Toast.makeText(context, "logout...", Toast.LENGTH_SHORT).show();

        RestService.getInstance(context)
                .logOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        //TODO: When a user logs out, I want to clear the Token, but I'm unable to interact with the SharePref,
                        //TODO...If you uncomment the code you'll see it crashes
                        Log.d(TAG, "onComplete Logout ");
                        mainScreen.logout();
                        savedPrefInteractor.saveToken("");
                        Log.d(TAG, "TOKEN ON LOGOUT onNext:" + savedPrefInteractor.getToken());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO: HTTP Returns 200 but it always lands here in onError
                        Log.e(TAG, "onError Logout !!! " + Log.getStackTraceString(e));
                        savedPrefInteractor.saveToken("");
                        Log.d(TAG, "TOKEN ON LOGOUT onError:" + savedPrefInteractor.getToken());

                        mainScreen.logout();
                    }
                });
    }
}
