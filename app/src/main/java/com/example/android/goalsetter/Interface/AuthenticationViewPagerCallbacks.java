package com.example.android.goalsetter.Interface;

import com.example.android.goalsetter.Models.User;

import androidx.annotation.Nullable;

public interface AuthenticationViewPagerCallbacks {

    /**
     * This call back method is used to control the AuthenticationViewPagerActivity viewpager signUp page
     *
     * @param signUp   if true, code to create an account will be executed, else it will just move
     *                 to signUp screen
     * @param user     the object of the user to store in db
     * @param password contains the password to use sign up
     */
    void signUpButtonClicked(boolean signUp, @Nullable User user, @Nullable String password);


    /**
     * This call back method is used to control the AuthenticationViewPagerActivity viewpager login page
     *
     * @param login if true, code to login will be executed, else it will just move
     *              to signUp screen
     */
    void loginButtonClicked(boolean login, @Nullable String email, @Nullable String password);

}
