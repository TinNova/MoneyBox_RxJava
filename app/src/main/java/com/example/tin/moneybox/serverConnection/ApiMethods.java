package com.example.tin.moneybox.serverConnection;

import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.body.PaymentBody;
import com.example.tin.moneybox.serverConnection.response.OneOffPaymentResponse;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;
import com.example.tin.moneybox.serverConnection.response.UserResponse;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface ApiMethods {

    /* This the the url, at loginUser we are attaching the LonginBody (email/pass/ISBD) Retrofit turns this into a Json */
    @POST("/users/login")
    @Headers("No_Authentication: true")
    Observable<UserResponse> loginUser(@Body LoginBody loginBody);

    /* It is now used for synchronous calls*/
    /** Regarding "No_Authentication"
     *  Tip: From: Jake Wharton in the video "Making Retrofit Work For You by Jake Wharton"
     * - The purpose is to allow you to add more ApiMethods which don't require a Token
     *   without needing to edit both the ApiMethods.class and TokenInterceptor.class
     */
    @POST("/users/login")
    @Headers("No_Authentication: true")
    Call<UserResponse> loginUserSync(@Body LoginBody loginBody);

    @GET("/investorproduct/thisweek")
    Observable<ProductResponse> getProducts();

    @POST("/users/logout")
    Completable logOut(); // Completable is used when a response isn't required

    @POST("/oneoffpayments")
    Observable<OneOffPaymentResponse> payment(@Body PaymentBody paymentBody);
}
