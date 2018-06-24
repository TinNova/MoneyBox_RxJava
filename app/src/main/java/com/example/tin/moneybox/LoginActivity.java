package com.example.tin.moneybox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.tin.moneybox.utils.EmailValidationUtils.isEmailValid;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginScreen {

    private static final String TAG = LoginActivity.class.getSimpleName();

    /* Key for Intent */
    public static final String USER_FIRST_NAME = "user_first_name";
    private static final String SESSION_TOKEN = "session_token";

    private LoginPresenter loginPresenter;

    private TextView emailTextView;
    private TextView passTextView;

    private Button loginButton;
    private EditText emailEditText;
    private EditText passEditText;

    private ProgressBar loadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Initialising all of the buttons */
        loadingIndicator = findViewById(R.id.pB_lgn_loading);
        loginButton = findViewById(R.id.btn_lgn_login);
        emailEditText = findViewById(R.id.eT_lgn_email);
        passEditText = findViewById(R.id.eT_lgn_pass);
        emailTextView = findViewById(R.id.tV_lgn_email);
        passTextView = findViewById(R.id.tV_lgn_pass);

        loginPresenter = new LoginPresenter(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get String from email and pass and send it to the presenter
                String email = emailEditText.getText().toString();
                String pass = passEditText.getText().toString();

                if (!pass.isEmpty() && isEmailValid(email)) {

                    loginPresenter.startLogin(LoginActivity.this, email, pass);
                } else {
                    Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.incorrect_login_details), Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    @Override
    public void showLoading() {

        loadingIndicator.setVisibility(View.VISIBLE);
        emailTextView.setVisibility(View.INVISIBLE);
        emailEditText.setVisibility(View.INVISIBLE);
        passTextView.setVisibility(View.INVISIBLE);
        passEditText.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
    }
}
