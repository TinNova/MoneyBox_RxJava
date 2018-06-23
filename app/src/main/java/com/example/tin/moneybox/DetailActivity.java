package com.example.tin.moneybox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tin.moneybox.models.Product;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailContract.DetailScreen {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private DetailPresenter detailPresenter;

    private ArrayList<ProductResponse.ProductModel> mProducts;

    int positionClicked;

    TextView moneyboxTv;
    Button depositBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.d(TAG, "DETAIL ACTIVITY onCreate");

        detailPresenter = new DetailPresenter(this);

        moneyboxTv = findViewById(R.id.tV_moneybox);
        depositBtn = findViewById(R.id.btn_depositMoney);

        Intent getIntent = getIntent();

        if (getIntent != null) {
            mProducts = getIntent.getParcelableArrayListExtra(MainActivity.PRODUCT_LIST);
            positionClicked = getIntent.getIntExtra(MainActivity.POSITION_CLICKED, -1);

            detailPresenter.prepareArrayListData(mProducts, positionClicked);

            depositBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    detailPresenter.depositMoney(DetailActivity.this);
                }
            });

        } else {
            Toast.makeText(this, "Error loading data, please try again.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void populateDetailView(int moneybox, String friendlyName) {

        //moneybox = Integer.parseInt("Your Moneybox: £" + Integer.parseInt(String.valueOf((moneybox))));
        setTitle(friendlyName);
        moneyboxTv.setText(String.valueOf(moneybox));

    }

    @Override
    public void updateMoneyBox(int moneybox) {

        moneyboxTv.setText(String.valueOf(moneybox));

        Toast.makeText(this, "£10 Deposited!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "DETAIL ACTIVITY onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "DETAIL ACTIVITY onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "DETAIL ACTIVITY onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "DETAIL ACTIVITY onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "DETAIL ACTIVITY onDestroy");

    }
}
