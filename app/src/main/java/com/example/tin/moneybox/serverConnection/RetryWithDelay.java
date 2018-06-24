package com.example.tin.moneybox.serverConnection;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    /**
     * Method which is called when a 401 or 404 or something else happened to allow for a break
     * before launching another request to login */
    public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.retryCount = 0;
    }

    @Override
    public Observable<?> apply(final Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap((Function<Throwable, Observable<?>>) throwable -> {
                    if (++retryCount < maxRetries) {
                        // When this Observable calls onNext, the original
                        // Observable will be retried (i.e. re-subscribed).
                        return Observable.timer(retryDelayMillis,
                                TimeUnit.MILLISECONDS);
                    }

                    // Max retries hit. Just pass the error along.
                    return Observable.error(throwable);
                });
    }
}
