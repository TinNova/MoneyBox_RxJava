package com.example.tin.moneybox;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tin.moneybox.serverConnection.RestService;
import com.example.tin.moneybox.serverConnection.response.UserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginScreen {

    private static final String TAG = LoginActivity.class.getSimpleName();

    /* Key for Intent */
    public static String USER_FIRST_NAME = "user_first_name";
    public static String USER_DATA_USER = "user_data_user";
    public static String USER_DATA_SESSION = "user_data_session";
    public static String SESSION_TOKEN = "session_token";

    private LoginPresenter loginPresenter;

    private Button loginButton;
    private EditText emailEditText;
    private EditText passEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Initialising all of the buttons */
        loginButton = findViewById(R.id.btn_Login);
        emailEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.passEditText);

        loginPresenter = new LoginPresenter(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get String from email and pass and send it to the presenter
                String email = emailEditText.getText().toString();
                String pass = passEditText.getText().toString();

                loginPresenter.startLogin(LoginActivity.this, email, pass);

            }
        });

//        /* This is getting the Products list, should be moved to MainPresenter
//            This is called on a successful or failed endpoint connection */
//        RestService.getInstance(getApplication()).getProducts()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(product -> { //Lambda code, it is the same as the above @overide onSubscribe ect...
//
//                    Log.d(TAG, "successul load of products " + product);
//                }, throwable -> {
//                    Log.e(TAG, "error while load products " + Log.getStackTraceString(throwable));
//                });
    }

    @Override
    public void launchMainActivity(String userFirstName, String sessionToken) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USER_FIRST_NAME, userFirstName);
        intent.putExtra(SESSION_TOKEN, sessionToken);

        startActivity(intent);
    }

    @Override
    public Context provideContext() {
        return this;
    }
}
