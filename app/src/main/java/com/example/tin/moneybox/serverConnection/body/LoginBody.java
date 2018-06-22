package com.example.tin.moneybox.serverConnection.body;

public class LoginBody {

    String Email;
    String Password;
    String Idfa;

    public LoginBody(String email, String password, String idfa) {
        Email = email;
        Password = password;
        Idfa = idfa;
    }
}
