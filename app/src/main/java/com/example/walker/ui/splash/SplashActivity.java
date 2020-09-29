package com.example.walker.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.walker.R;
import com.example.walker.service.SyncService;
import com.example.walker.ui.base.BaseActivity;
import com.example.walker.ui.login.LoginActivity;
import com.example.walker.ui.main.MainActivity;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SplashActivity.this);
    }

    /**
     * Making the screen wait so that the  branding can be shown
     */
    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openMainActivity(Long userId) {
        Intent intent = MainActivity.getStartIntent(SplashActivity.this);
        String myJson = new Gson().toJson(userId);
        intent.putExtra("userId", myJson);
        startActivity(intent);
        finish();
    }

    @Override
    public void startSyncService() {
        //SyncService.start(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

    }
}
