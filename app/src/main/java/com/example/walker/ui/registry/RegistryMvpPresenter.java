package com.example.walker.ui.registry;

import com.example.walker.model.User;
import com.example.walker.ui.base.MvpPresenter;

public interface RegistryMvpPresenter<V extends RegistryMvpView> extends MvpPresenter<V> {

    void onUserCreate(String email, String password, User user);
}
