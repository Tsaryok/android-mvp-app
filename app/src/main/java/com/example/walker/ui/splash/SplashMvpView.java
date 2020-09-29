package com.example.walker.ui.splash;

import com.example.walker.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    void openLoginActivity();

    void openMainActivity(Long userId);

    void startSyncService();
}
