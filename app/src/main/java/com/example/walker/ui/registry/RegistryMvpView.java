package com.example.walker.ui.registry;

import com.example.walker.ui.base.MvpView;
import com.google.firebase.auth.FirebaseUser;

public interface RegistryMvpView extends MvpView {

    void createUser();

    void openMainActivity();

}
