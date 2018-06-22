package com.example.tin.moneybox.models;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Tin on 17/06/2018.
 */

public class Product implements Parcelable {

    private final int investorProductId;
    private final String investorProductType;
    private final int productId;
    private final int moneybox; // How much the user has saved this week so far and is the users 'Moneybox'
    private final int subscriptionAmount; // What the current weekly subscription is set to
    private final int planValue; // The current account balance
    private final int sytd; // How much the user has contributed in the current tax year
    private final int maximumDeposit;
    private final String friendlyName;

    public Product(int investorProductId, String investorProductType, int productId, int moneybox, int subscriptionAmount, int planValue, int sytd, int maximumDeposit, String friendlyName) {
        this.investorProductId = investorProductId;
        this.investorProductType = investorProductType;
        this.productId = productId;
        this.moneybox = moneybox;
        this.subscriptionAmount = subscriptionAmount;
        this.planValue = planValue;
        this.sytd = sytd;
        this.maximumDeposit = maximumDeposit;
        this.friendlyName = friendlyName;
    }

    protected Product(Parcel in) {
        investorProductId = in.readInt();
        investorProductType = in.readString();
        productId = in.readInt();
        moneybox = in.readInt();
        subscriptionAmount = in.readInt();
        planValue = in.readInt();
        sytd = in.readInt();
        maximumDeposit = in.readInt();
        friendlyName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(investorProductId);
        parcel.writeString(investorProductType);
        parcel.writeInt(productId);
        parcel.writeInt(moneybox);
        parcel.writeInt(subscriptionAmount);
        parcel.writeInt(planValue);
        parcel.writeInt(sytd);
        parcel.writeInt(maximumDeposit);
        parcel.writeString(friendlyName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };


    public int getInvestorProductId() {
        return investorProductId;
    }

    public String getInvestorProductType() {
        return investorProductType;
    }

    public int getProductId() {
        return productId;
    }

    public int getMoneybox() {
        return moneybox;
    }

    public int getSubscriptionAmount() {
        return subscriptionAmount;
    }

    public int getPlanValue() {
        return planValue;
    }

    public int getSytd() {
        return sytd;
    }

    public int getMaximumDeposit() {
        return maximumDeposit;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
