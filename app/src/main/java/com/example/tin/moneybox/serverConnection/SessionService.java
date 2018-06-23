package com.example.tin.moneybox.serverConnection;


import com.example.tin.moneybox.network.NetworkConnection;
import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.response.UserResponse;
import com.example.tin.moneybox.utils.Const;

import io.reactivex.Observable;

public class SessionService {

    ApiMethods apiMethods;
    SavedPreferencesInteractor savedPrefInteractor;

    public SessionService(ApiMethods apiMethods, SavedPreferencesInteractor savedPrefInteractor) {

        this.apiMethods = apiMethods;
        this.savedPrefInteractor = savedPrefInteractor;
    }

    /* If there is an error in the reposnse, this is called to re-login a user. You will notice it looks
    * almost identical to the login code in the login presenter/acitvity because it is the same */
    public Observable<UserResponse> observeToken() {
        // Modify to return an Observable that when subscribed to
        // will make the network request to get the Session cookie.
        return apiMethods.loginUser(new LoginBody(Const.EMAIL, Const.PASS, NetworkConnection.IDFA_VALUE))
                .map(user -> {

                    //here we saving user BearerToken on succesfull login
                    savedPrefInteractor.saveToken(user.Session.BearerToken);
                    return user;
                });
    }
}
