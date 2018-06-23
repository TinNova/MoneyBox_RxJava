package com.example.tin.moneybox.serverConnection.body;

/**
 * Created by Tin on 23/06/2018.
 */

public class PaymentBody {

    int Amount;
    int InvestorProductId;

    public PaymentBody(int amount, int investorProductId) {
        Amount = amount;
        InvestorProductId = investorProductId;
    }
}
