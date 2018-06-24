package com.example.tin.moneybox.serverConnection.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProductResponse {

    ArrayList<ProductModel> Products;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "Products=" + Products +
                '}';
    }

    public static class ProductModel implements Parcelable {

        public int InvestorProductId;
        public String InvestorProductType;
        public int ProductId;
        public int Moneybox; // How much the user has saved this week so far and is the users 'Moneybox'
        public int SubscriptionAmount; // What the current weekly subscription is set to
        public int PlanValue; // The current account balance
        public int Sytd; // How much the user has contributed in the current tax year
        public int MaximumDeposit;
        public String FriendlyName;
        public SingleProduct Product;


        protected ProductModel(Parcel in) {
            InvestorProductId = in.readInt();
            InvestorProductType = in.readString();
            ProductId = in.readInt();
            Moneybox = in.readInt();
            SubscriptionAmount = in.readInt();
            PlanValue = in.readInt();
            Sytd = in.readInt();
            MaximumDeposit = in.readInt();
            FriendlyName = in.readString();
            Product = in.readParcelable(SingleProduct.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(InvestorProductId);
            dest.writeString(InvestorProductType);
            dest.writeInt(ProductId);
            dest.writeInt(Moneybox);
            dest.writeInt(SubscriptionAmount);
            dest.writeInt(PlanValue);
            dest.writeInt(Sytd);
            dest.writeInt(MaximumDeposit);
            dest.writeString(FriendlyName);
            dest.writeParcelable(Product, 0);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
            @Override
            public ProductModel createFromParcel(Parcel in) {
                return new ProductModel(in);
            }

            @Override
            public ProductModel[] newArray(int size) {
                return new ProductModel[size];
            }
        };

        @Override
        public String toString() {
            return "ProductModel{" +
                    "InvestorProductId=" + InvestorProductId +
                    "InvestorProductType=" + InvestorProductType +
                    "ProductId=" + ProductId +
                    "moneybox=" + Moneybox +
                    "SubscriptionAmount=" + SubscriptionAmount +
                    "PlanValue=" + PlanValue +
                    "Sytd=" + Sytd +
                    "MaximumDeposit=" + MaximumDeposit +
                    "FriendlyName=" + FriendlyName +
                    '}';
        }


        public int getInvestorProductId() {
            return InvestorProductId;
        }

        public String getInvestorProductType() {
            return InvestorProductType;
        }

        public int getProductId() {
            return ProductId;
        }

        public int getMoneybox() {
            return Moneybox;
        }

        public int getSubscriptionAmount() {
            return SubscriptionAmount;
        }

        public int getPlanValue() {
            return PlanValue;
        }

        public int getSytd() {
            return Sytd;
        }

        public int getMaximumDeposit() {
            return MaximumDeposit;
        }

        public String getFriendlyName() {
            return FriendlyName;
        }
    }

    public ArrayList<ProductModel> getProducts() {
        return Products;
    }


    public static class SingleProduct implements Parcelable {
        public String FriendlyName;

        protected SingleProduct(Parcel in) {
            FriendlyName = in.readString();
        }

        public static final Creator<SingleProduct> CREATOR = new Creator<SingleProduct>() {
            @Override
            public SingleProduct createFromParcel(Parcel in) {
                return new SingleProduct(in);
            }

            @Override
            public SingleProduct[] newArray(int size) {
                return new SingleProduct[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(FriendlyName);
        }
    }
}
