package com.example.tin.moneybox.serverConnection.body;

public class LoginBody {

    private final String Email;
    private final String Password;
    private final String Idfa;

    public LoginBody(String email, String password, String idfa) {
        Email = email;
        Password = password;
        Idfa = idfa;
    }
}
