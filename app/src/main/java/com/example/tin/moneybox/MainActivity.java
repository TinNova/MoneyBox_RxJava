package com.example.tin.moneybox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tin.moneybox.adapters.ProductAdapter;
import com.example.tin.moneybox.models.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.MainScreen, ProductPositionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    /* Key for Intent */
    public static String PRODUCT_LIST = "product_list";
    public static String POSITION_CLICKED = "positionClicked";


    private MainPresenter mainPresenter;

    String firstName;
    String lastName;
    String title;

    private Button logOutButton;

    /*
     * Needed to populate the Adapter and the RecyclerView
     */
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    /* Used for savedInstanceState */
    private ArrayList<Product> mProducts;
    String mUser;
    String mSession;


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MAIN ACTIVITY onStart");


        mainPresenter = new MainPresenter(this);

        logOutButton = findViewById(R.id.btn_Logout);

        /* Setting up the RecyclerView and Adapter*/
        mRecyclerView = findViewById(R.id.rV_productList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ProductAdapter(null, getApplicationContext(), this);
        mRecyclerView.setAdapter(mAdapter);

        Intent getIntent = getIntent();

        if (getIntent != null) {
            mUser = getIntent.getStringExtra(LoginActivity.USER_DATA_USER);
            mSession = getIntent.getStringExtra(LoginActivity.USER_DATA_SESSION);

            Log.d(TAG, "mUsers: " + mUser);
            Log.d(TAG, "mUsers: " + mSession);


//            firstName = mUser.get(0).getUserFirstName();
//            lastName = mUser.get(0).getUserLastName();
//            title = "Welcome, " + " " + firstName + " " + lastName;
//
//            setTitle(title);

            //mainPresenter.getThisWeekResponse(MainActivity.this, mUser);

        } else {
            Toast.makeText(this, "Error loading data, please try again.", Toast.LENGTH_SHORT).show();
        }

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainPresenter.startLogOut(MainActivity.this);
            }
        });

        Log.d(TAG, "mUser: " + mUser);

    }

    @Override
    public void showProducts(ArrayList<Product> products) {
        mProducts = products;
        mAdapter.setProducts(products);
        //hideLoading();
    }

    @Override
    public void logout() {

        //TODO: Delete username, password and saved BearerToken within this method
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void btnProductClick(View v, int position) {

        //TODO: The reason we have created the ProductPositionListener class I think is because
        //TODO..the data is passed into the Adapter after the Adapter and RecyclerView has been created
        //TODO..to solve this, can we try creating the Adapter and RecyclerView within the method showProducts
        //TODO..instead of within onCreate?
        Log.d(TAG, "Item Position: " + position);


        Toast.makeText(this, "Clicked Position " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(POSITION_CLICKED, position);
        intent.putParcelableArrayListExtra(PRODUCT_LIST, mProducts);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "MAIN ACTIVITY onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Called when user returns to MainActivity from DetailActivity, it ensures data is updated */
        //mainPresenter.getThisWeekResponse(MainActivity.this, mUser);

        Log.d(TAG, "MAIN ACTIVITY onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "MAIN ACTIVITY onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "MAIN ACTIVITY onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "MAIN ACTIVITY onDestroy");

    }

}
