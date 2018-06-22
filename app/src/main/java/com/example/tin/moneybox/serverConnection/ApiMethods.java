package com.example.tin.moneybox.serverConnection;

import com.example.tin.moneybox.models.Product;
import com.example.tin.moneybox.models.User;
import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;
import com.example.tin.moneybox.serverConnection.response.UserResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiMethods {

    /* This the the url, at loginUser we are attaching the LonginBody (email/pass/ISBD) Retrofit turns this into a Json */
    @POST("/users/login")
    Observable<UserResponse> loginUser(@Body LoginBody loginBody);

    @GET("/investorproduct/thisweek")
    Observable<ProductResponse> getProducts();
}
