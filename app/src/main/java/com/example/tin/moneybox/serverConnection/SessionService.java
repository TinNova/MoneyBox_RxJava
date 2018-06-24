package com.example.tin.moneybox.serverConnection;


import com.example.tin.moneybox.serverConnection.body.LoginBody;
import com.example.tin.moneybox.serverConnection.response.UserResponse;
import com.example.tin.moneybox.utils.Const;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

class SessionService {

    private final ApiMethods apiMethods;
    private final SavedPreferencesInteractor savedPrefInteractor;

    public SessionService(ApiMethods apiMethods, SavedPreferencesInteractor savedPrefInteractor) {

        this.apiMethods = apiMethods;
        this.savedPrefInteractor = savedPrefInteractor;
    }

    /* If there is an error in the response, this is called to re-login a user. You will notice it looks
    * almost identical to the login code in the login presenter/acitvity because it is the same */
    public Observable<UserResponse> observeToken() {
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
