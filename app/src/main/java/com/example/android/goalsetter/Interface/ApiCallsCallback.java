package com.example.android.goalsetter.Interface;

import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;

public interface ApiCallsCallback {
    /**
     * A call back method when register api call is done
     *
     * @param userDetail a boolean indicated whether creating the user is successful or not
     * @param token
     */
    void register(User userDetail, String token);

    /**
     * Call back method when login api call is donr
     *
     * @param token a string of the user's token
     */
    void login(RegisterResponseDataModel token);

    /**
     * Call back when dashBoard api call is done
     *
     * @param user an instance of the user depending on the header token
     */
    void dashBoard(User user);
}
