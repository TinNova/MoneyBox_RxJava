package com.example.tin.moneybox.serverConnection;


import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.response.UserResponse;
import com.example.tin.moneybox.utils.Const;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class SessionService {

    ApiMethods apiMethods;
    SavedPreferencesInteractor savedPrefInteractor;

    public SessionService(ApiMethods apiMethods, SavedPreferencesInteractor savedPrefInteractor) {

        this.apiMethods = apiMethods;
        this.savedPrefInteractor = savedPrefInteractor;
    }

    /* If there is an error in the response, this is called to re-login a user. You will notice it looks
    * almost identical to the login code in the login presenter/acitvity because it is the same */
    public Observable<UserResponse> observeToken() {
        // Modify to return an Observable that when subscribed to
        // will make the network request to get the Session cookie.
        //TODO: Currently the token is only saved here, which is the code that runs when there is a 401 error
        //TODO:...Because it is saved here only, it means when a user first logs in, a 401 error is delivered, you can see this in the log
        return apiMethods.loginUser(new LoginBody(Const.EMAIL, Const.PASS, Const.IDFA_VALUE))
                .map(new Function<UserResponse, UserResponse>() {
                    @Override
                    public UserResponse apply(UserResponse userResponse) throws Exception {

                        //here we saving user BearerToken on successful login
                        savedPrefInteractor.saveToken(userResponse.Session.BearerToken);
                        return userResponse;
                    }
                });
    }
}
