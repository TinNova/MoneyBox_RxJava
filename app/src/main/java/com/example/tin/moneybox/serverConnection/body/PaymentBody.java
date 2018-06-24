package com.example.tin.moneybox.serverConnection.body;


public class PaymentBody {

    private final int Amount;
    private final int InvestorProductId;

    public PaymentBody(int amount, int investorProductId) {
        Amount = amount;
        InvestorProductId = investorProductId;
    }
}
