package com.example.tin.moneybox.network;

import com.example.tin.moneybox.models.Product;
import com.example.tin.moneybox.models.User;

import java.util.ArrayList;

public interface NetworkListener {

    interface LoginListener {
        void getResponse(ArrayList<User> user);
    }

    interface ThisWeekListener {
        void getResponse(ArrayList<Product> products);
    }

    interface LogoutListener {
        void getResponse(String string);
    }

    interface OneOffPaymentListener {
        void getResponse(int amountInMoneybox);
    }
}
