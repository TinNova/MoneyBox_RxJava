package com.example.tin.moneybox.serverConnection;

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


class TokenInterceptor implements Interceptor {

    private static final int UNAUTHORISED_ERROR = 401;

    /* Header Keys */
    private static final String APP_ID_KEY = "AppId";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String APP_VERSION_KEY = "appVersion";
    private static final String API_VERSION_KEY = "apiVersion";
    private static final String AUTHORIZATION_KEY = "Authorization";
    /* Header Values */
    private static final String APP_ID_VALUE = BuildConfig.MB_API_KEY;
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String APP_VERSION_VALUE = "4.11.0";
    private static final String API_VERSION_VALUE = "3.0.0";
    private static final String BEARER = "Bearer ";

    private final Lock lock = new ReentrantLock();
    private final ApiMethods INSTANC;
    private final SavedPreferencesInteractor mSavedPreferencesInteractor;

    public TokenInterceptor(ApiMethods INSTANCE) {
        INSTANC = INSTANCE;
        mSavedPreferencesInteractor = SavedPreferencesInteractor.getInstance();
    }

    /** Here we are stating, what we are listening/filtering for. We are listening/filtering for
     * a 401 error */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        /** If the response contains 401, then we need to launch the .loginUserSync API, pass in the users
         * Email and Password in order to retrieve a valid token*/
        if (response.code() == UNAUTHORISED_ERROR) {
            refreshToken(chain);
        }
        return chain.proceed(addToken(chain));
    }

    private void refreshToken(@NonNull Chain chain) throws IOException {
        // lock ensures only this thread and no other has access to the UserResponse Model
        if (lock.tryLock()) {
            try {
                /**
                 * Here we are doing a synchronous API call with .loginUserSync to retrieve the token
                 * Notice the ".execute()", this means it's an API call
                 */
                final UserResponse userResponse = INSTANC.loginUserSync(new LoginBody(Const.EMAIL, Const.PASS, Const.IDFA_VALUE))
                        .execute()
                        .body();
                /** Here we are saving the token retrieved */
                mSavedPreferencesInteractor.saveToken(userResponse.getSession().getBearerToken());
            } finally {
                /** We are unlocking access to the UserResponse Model*/
                lock.unlock();
            }
        } else {
            lock.lock();
        }
        chain.proceed(addToken(chain));
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
        /** Here we add the the Headers to the API call and insert the newly retrieved Token */
        requestBuilder
                .addHeader(APP_ID_KEY, APP_ID_VALUE)
                .addHeader(APP_VERSION_KEY, APP_VERSION_VALUE)
                .addHeader(API_VERSION_KEY, API_VERSION_VALUE)
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);

        if (chain.request().header("No_Authentication") == null) {

            requestBuilder
                    .addHeader(AUTHORIZATION_KEY, BEARER + token);
        }

        return requestBuilder
                .build();
    }
}
