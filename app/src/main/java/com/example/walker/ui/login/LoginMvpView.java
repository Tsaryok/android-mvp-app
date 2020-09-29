package com.example.walker.ui.login;


import com.example.walker.ui.base.MvpView;

public interface LoginMvpView extends MvpView {

    void openMainActivity();

    String getStringById(int id);

}
