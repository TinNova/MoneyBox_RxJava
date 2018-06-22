package com.example.tin.moneybox.utils;

import android.util.Log;

import com.example.tin.moneybox.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductJsonUtils {

    private static final String TAG = ProductJsonUtils.class.getSimpleName();

    /* Values, "TW" stands for ThisWeek */
    private static final String TW_IVESTOR_PRODUCT_ID = "InvestorProductId";
    private static final String TW_IVESTOR_PRODUCT_TYPE = "InvestorProductType";
    private static final String TW_PRODUCT_ID = "ProductId";
    private static final String TW_MONEYBOX = "Moneybox";
    private static final String TW_SUBSCRIPTION_AMOUNT = "SubscriptionAmount";
    private static final String TW_PLAN_VALUE = "PlanValue";
    private static final String TW_SYTD = "Sytd";
    private static final String TW_MAXIMUM_DEPOSIT = "MaximumDeposit";
    private static final String TW_FRIENDLY_NAME = "FriendlyName";
    /* JSON Objects/Arrays */
    private static final String JSON_ARRAY_PRODUCTS = "Products";
    private static final String JSON_OBJECT_PRODUCT = "Product";

    public static ArrayList<Product> parseProductJson(String response) {

        ArrayList<Product> mProducts = new ArrayList<>();

        try {

            /* Define the response as a JsonObject */
            JSONObject thisWeekJsonObject = new JSONObject(response);

            JSONArray productsJsonArray = thisWeekJsonObject.getJSONArray(JSON_ARRAY_PRODUCTS);

            /* Using a for loop to cycle through each JsonObject within the listJsonArray */
            for (int i = 0; i < productsJsonArray.length(); i++) {

                /* Get the ith forecast in the JSON and define it as a JsonObject */
                JSONObject productsJsonObject = productsJsonArray.getJSONObject(i);

                int investorProductId = productsJsonObject.getInt(TW_IVESTOR_PRODUCT_ID);
                String investorProductType = productsJsonObject.getString(TW_IVESTOR_PRODUCT_TYPE);
                int productId = productsJsonObject.getInt(TW_PRODUCT_ID);
                int moneybox = productsJsonObject.getInt(TW_MONEYBOX);
                int subscriptionAmount = productsJsonObject.getInt(TW_SUBSCRIPTION_AMOUNT);
                int planValue = productsJsonObject.getInt(TW_PLAN_VALUE);
                int sytd = productsJsonObject.getInt(TW_SYTD);
                int maximumDeposit = productsJsonObject.getInt(TW_MAXIMUM_DEPOSIT);

                JSONObject productJsonObject = productsJsonObject.getJSONObject(JSON_OBJECT_PRODUCT);

                String friendlyName = productJsonObject.getString(TW_FRIENDLY_NAME);

                Product product = new Product(
                        investorProductId,
                        investorProductType,
                        productId,
                        moneybox,
                        subscriptionAmount,
                        planValue,
                        sytd,
                        maximumDeposit,
                        friendlyName
                );

                mProducts.add(product);
                Log.d(TAG, "Product List: " + product);

                Log.d(TAG, "friendlyName: " + friendlyName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Product List: " + mProducts);

        return mProducts;
    }
}
