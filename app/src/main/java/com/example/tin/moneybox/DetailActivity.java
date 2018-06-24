package com.example.tin.moneybox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tin.moneybox.serverConnection.response.ProductResponse;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailContract.DetailScreen {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private final String SAVED_MONEYBOX = "saved_moneybox";
    private final String SAVED_PRODUCTS_ARRAY = "save_products_array";
    private final String SAVED_POSITION_CLICKED = "saved_position_clicked";

    private DetailPresenter detailPresenter;

    private ArrayList<ProductResponse.ProductModel> mProducts;

    private int positionClicked;
    private int mMoneybox;

    private TextView moneyboxTv;
    private Button depositBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.d(TAG, "DETAIL ACTIVITY onCreate");

        detailPresenter = new DetailPresenter(this);

        moneyboxTv = findViewById(R.id.tV_moneybox);
        depositBtn = findViewById(R.id.btn_depositMoney);

        /* If There isn't a savedInstanceState, Download The Data And Build The RecyclerView */
        if (savedInstanceState != null) {
            mMoneybox = savedInstanceState.getInt(SAVED_MONEYBOX);
            mProducts = savedInstanceState.getParcelableArrayList(SAVED_PRODUCTS_ARRAY);
            positionClicked = savedInstanceState.getInt(SAVED_POSITION_CLICKED);
            detailPresenter.prepareArrayListData(mProducts, positionClicked);

        } else {
            Intent getIntent = getIntent();

            if (getIntent != null) {
                mProducts = getIntent.getParcelableArrayListExtra(MainActivity.PRODUCT_LIST);
                positionClicked = getIntent.getIntExtra(MainActivity.POSITION_CLICKED, -1);

                detailPresenter.prepareArrayListData(mProducts, positionClicked);

            } else {
                Toast.makeText(this, "Error loading data, please try again.", Toast.LENGTH_SHORT).show();
            }
        }

        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                detailPresenter.depositMoney(DetailActivity.this);
            }
        });
    }

    @Override
    public void populateDetailView(int moneybox, String friendlyName) {

        setTitle(friendlyName);
        if (mMoneybox > moneybox ) {
            moneyboxTv.setText(String.valueOf(mMoneybox));
        } else {
            moneyboxTv.setText(String.valueOf(moneybox));
        }
    }

    @Override
    public void updateMoneyBox(int moneybox) {

        mMoneybox = moneybox;
        moneyboxTv.setText(String.valueOf(mMoneybox));

        Toast.makeText(this, "£10 Deposited!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        /* Saving mWeather to be reused should the device rotate */
        outState.putInt(SAVED_MONEYBOX, mMoneybox);
        outState.putParcelableArrayList(SAVED_PRODUCTS_ARRAY, mProducts);
        outState.putInt(SAVED_POSITION_CLICKED, positionClicked);
    }
}
