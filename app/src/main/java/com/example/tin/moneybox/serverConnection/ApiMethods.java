package com.example.tin.moneybox.serverConnection;

import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.body.PaymentBody;
import com.example.tin.moneybox.serverConnection.response.LogoutResponse;
import com.example.tin.moneybox.serverConnection.response.OneOffPaymentResponse;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;
import com.example.tin.moneybox.serverConnection.response.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiMethods {

    /* This the the url, at loginUser we are attaching the LonginBody (email/pass/ISBD) Retrofit turns this into a Json */
    @POST("/users/login")
    Observable<UserResponse> loginUser(@Body LoginBody loginBody);

    @GET("/investorproduct/thisweek")
    Observable<ProductResponse> getProducts();

    @POST("/users/logout")
    Observable<LogoutResponse> logOut();

    @POST("/oneoffpayments")
    Observable<OneOffPaymentResponse> payment(@Body PaymentBody paymentBody);

}
