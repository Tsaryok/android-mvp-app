package com.example.walker.ui.login;

import com.example.walker.di.PerActivity;
import com.example.walker.ui.base.MvpPresenter;

@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onLoginClick(String email, String password);

    void onFacebookLoginClick();

    void onSignIn();

    void firebaseAuthWithGoogle(String idToken);


}
