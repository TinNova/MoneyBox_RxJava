package com.example.tin.moneybox.serverConnection;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.body.PaymentBody;
import com.example.tin.moneybox.serverConnection.response.LogoutResponse;
import com.example.tin.moneybox.serverConnection.response.OneOffPaymentResponse;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;
import com.example.tin.moneybox.serverConnection.response.UserResponse;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The main idea that with RX you can react on failed execution, and you able to retry your initial
 * call
 * <p>
 * So you need to add {@link RetryWithSessionRefresh} to each your call, because each of your call can
 * receive 401, or whatever 400 error
 * <p>
 * Based on that, retryWhen() of {@link Observable} works on failed execution
 * <p>
 * We launch our {@link RetryWithSessionRefresh} on error which has 2 constants, max retries and delay
 * It has also it own {@link RetryWithDelay} which allows us to wait 2 seconds before next attempt for login
 * also this observable call {@link SessionService}
 */
public class RestService {

    static String TAG = "ololo";

    private static ApiMethods INSTANCE;
    private static SessionService sessionSerivce;
    private static RestService restService;
    private static SavedPreferencesInteractor savedPrefInteractor;

    public static RestService getInstance(Context application) {

        /* We only want to create this once
        * This if statement says, if the instance is null, create, else return the existing instance*/
        if (INSTANCE == null) {

            restService = new RestService();
            savedPrefInteractor = new SavedPreferencesInteractor(application);

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api-test00.moneyboxapp.com/")
                    .client(provideOkHttp(savedPrefInteractor))
                    .build();

            INSTANCE = retrofit.create(ApiMethods.class);
            sessionSerivce = new SessionService(INSTANCE, savedPrefInteractor);
        }

        return restService;
    }

    private static OkHttpClient provideOkHttp(final SavedPreferencesInteractor savedPrefInteractor) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {

                    /**
                     * So this interceptor will add for EACH your call (even first login)
                     * auth header
                     * For the login we actually do not care if BearerToken will be or not
                     */
                    Log.d("BUMP", "we here " + savedPrefInteractor.getToken());
                    Request newRequest;

                    /* If we are logging in, we don't need the Token */
                    if (chain.request().url().toString().contains("login")) {

                        newRequest = chain.request().newBuilder()
                                .addHeader("AppId", "3a97b932a9d449c981b595")
                                .addHeader("appVersion", "4.11.0")
                                .addHeader("apiVersion", "3.0.0")
                                .addHeader("Content-Type", "application/json")
                                .build();

                        /* else, we are doing another endpoint, so we need the bearer*/
                    } else {

                        newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + savedPrefInteractor.getToken())
                                .addHeader("AppId", "3a97b932a9d449c981b595")
                                .addHeader("appVersion", "4.11.0")
                                .addHeader("apiVersion", "3.0.0")
                                .addHeader("Content-Type", "application/json")
                                .build();

                    }

                    /* For loop that brings the headers, for debugging perposes, it has to be a loop
                     * because every header has the same name ".names()" */
                    for (String name : newRequest.headers().names()) {

                        Log.d(TAG, newRequest.url().toString() + " name " + name + " val " + newRequest.headers().get(name));

                    }
                    return chain.proceed(newRequest);
                })
                .build();
    }

    /* Returning the message from the server (whether succesful or otherwise)
    * This is sent to the code that started the connection within onError or onNext for example. */
    public Observable<UserResponse> login(LoginBody loginBody) {
        // Here we receive the response, (which is the UserReponse which is already parsed when it arrives here,
        // We don't do anything with it here, we just return it.
        return INSTANCE.loginUser(loginBody);
    }

    public Observable<ProductResponse> getProducts() {

        // Here we are inserting .retryWhen() this is to handle an instance of an error being returned
        return INSTANCE.getProducts().retryWhen(new RetryWithSessionRefresh(sessionSerivce));
    }

    public Observable<LogoutResponse> logOut() {

        return INSTANCE.logOut();
    }

    public Observable<OneOffPaymentResponse> payment(PaymentBody paymentBody) {

        return INSTANCE.payment(paymentBody).retryWhen(new RetryWithSessionRefresh(sessionSerivce));
    }

}
