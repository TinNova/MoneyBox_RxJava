package com.example.tin.moneybox.serverConnection;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

class RetryWithSessionRefresh implements Function<Observable<? extends Throwable>, Observable<?>> {

    private String TAG = "RetrySession";

    private final SessionService sessionSerivce;
    private final int maxRetries = 3;
    private final int delay = 2000;

    public RetryWithSessionRefresh(SessionService sessionSerivce) {
        this.sessionSerivce = sessionSerivce;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> attempts) {

        // If this fails after 3 attempts it will be sent to onError which is in the LoginActivity/Presenter
        return attempts
                .flatMap(new Function<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Throwable throwable) throws Exception {
                        return sessionSerivce.observeToken()
                                .retryWhen(new RetryWithDelay(maxRetries, delay));
                    }
                });
    }
}
