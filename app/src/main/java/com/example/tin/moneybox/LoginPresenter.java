package com.example.tin.moneybox;


import android.content.Context;
import android.util.Log;

import com.example.tin.moneybox.serverConnection.RestService;
import com.example.tin.moneybox.serverConnection.SavedPreferencesInteractor;
import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.response.UserResponse;
import com.example.tin.moneybox.utils.Const;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginContract.LoginScreen loginScreen;

    LoginPresenter(LoginContract.LoginScreen screen) {
        this.loginScreen = screen;
        SavedPreferencesInteractor.context(screen.provideContext());
        this.savedPrefInteractor = SavedPreferencesInteractor.getInstance();
    }

    SavedPreferencesInteractor savedPrefInteractor;

    //TODO: Here we need to save the Token in SharedPref
    @Override
    public void startLogin(Context context, String email, String pass) {

        Log.d(TAG, "email & password: " + email + ", " + pass);

        //Show Loading Screen

        /* RxJava of Retrofit Method for login, this logs user in */
        RestService.getInstance(context)
                .loginUser(new LoginBody(Const.EMAIL, Const.PASS, Const.IDFA_VALUE))
                // Above we are:
                // 1. Calling .login, an observable
                // 2. Creating a new instance of the LoginBody and passing in the Username and Password the user inserted and the IDFA
                // 3. .login will POST this data to the .login as an observable
                // 4. In the response we don't recieve the Json, instead it is already parsed by Json

                .subscribeOn(Schedulers.io())
                // Above we are:
                // 1. Subscribing to the observable (i.e the parsed successful response or the error response that is returned (we are subscribing to the response that is returned)
                // 2. In "Schedulers.io()" we are Specifying the thread the stream should work within (io = Input Output)
                // RxJava/RxAndroid Schedulers are prebuilt threads where you can compute data away from the MainThread

                .observeOn(AndroidSchedulers.mainThread()) // On which thread we want to see result
                // Above we are:
                // 1. Specifying on which thread we want to see the response, here we are stating we want it on the mainThread()

                .subscribe(new Observer<UserResponse>() {
                    // Above we are:
                    // 1. .subscribe this is where we can interact with the result (error or successful)
                    // 2. Here we can specify what to do once we have a successful response i.e launch the next activity
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        // Called when we have a successful response
                        /* The user data is provided as a Json by Retrofit */

                        Log.d(TAG, "successful logged user " + userResponse);
                        Log.d(TAG, "successful logged user " + userResponse.getSession().getBearerToken());

                        savedPrefInteractor.saveToken(userResponse.getSession().getBearerToken());

                        String userFirstName = userResponse.getUserModel().getFirstName();
                        String sessionToken = userResponse.getSession().getBearerToken();

                        loginScreen.launchMainActivity(userFirstName, sessionToken);
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
