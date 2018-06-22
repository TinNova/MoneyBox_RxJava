package com.example.tin.moneybox.serverConnection.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {

    List<ProductModel> Products;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "Products=" + Products +
                '}';
    }

    private class ProductModel {

        public int InvestorProductId;

        @Override
        public String toString() {
            return "ProductModel{" +
                    "InvestorProductId=" + InvestorProductId +
                    '}';
        }

    }
}
