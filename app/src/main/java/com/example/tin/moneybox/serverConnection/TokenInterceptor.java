package com.example.tin.moneybox.serverConnection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.tin.moneybox.BuildConfig;
import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.response.UserResponse;
import com.example.tin.moneybox.utils.Const;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class TokenInterceptor implements Interceptor {

    private static final int UNAUTHORISED_ERROR = 401;

    private static final String APP_ID = BuildConfig.MB_API_KEY;

    private final Lock lock = new ReentrantLock();
    private final ApiMethods INSTANC;
    private final SavedPreferencesInteractor mSavedPreferencesInteractor;

    public TokenInterceptor(ApiMethods INSTANCE) {
        INSTANC = INSTANCE;
        mSavedPreferencesInteractor = SavedPreferencesInteractor.getInstance();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == UNAUTHORISED_ERROR) {
            refreshToken(chain);
        }
        return chain.proceed(addToken(chain));
    }

    private Response refreshToken(@NonNull Chain chain) throws IOException {
        if (lock.tryLock()) {
            try {
                final UserResponse userResponse = INSTANC.loginUserSync(new LoginBody(Const.EMAIL, Const.PASS, Const.IDFA_VALUE)).execute()
                        .body();
                mSavedPreferencesInteractor.saveToken(userResponse.getSession().getBearerToken());
            } finally {
                lock.unlock();
            }
        } else {
            lock.lock();
        }
        return chain.proceed(addToken(chain));
    }

    private Request addToken(Chain chain) {
        final String token = mSavedPreferencesInteractor.getToken();

        final Request.Builder requestBuilder = chain.request().newBuilder();
        if (!TextUtils.isEmpty(token)) {
            HttpUrl httpUrl = chain.request().url()
                    .newBuilder()
                    .build();
            requestBuilder.url(httpUrl);
        }

        Log.d("BUMP", "TOKEN RETRIEVED " + token);
        requestBuilder
                .addHeader("AppId", APP_ID)
                .addHeader("appVersion", "4.11.0")
                .addHeader("apiVersion", "3.0.0")
                .addHeader("Content-Type", "application/json");


                    /* If we are logging in, we don't need the Token */
        if (!chain.request().url().toString().contains("login")) {

            requestBuilder
                    .addHeader("Authorization", "Bearer " + token);

                        /* else, we are doing another endpoint, so we need the bearer*/
        }

        return requestBuilder
                .build();
    }
}
