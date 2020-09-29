package com.example.walker.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.walker.R;
import com.example.walker.ui.base.BaseActivity;
import com.example.walker.ui.offers.OffersFragment;
import com.example.walker.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        setUp();
    }

    public void showOffersFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, OffersFragment.newInstance(), OffersFragment.TAG)
                .commit();
    }

    public void showProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, ProfileFragment.newInstance(), ProfileFragment.TAG)
                .commit();
    }

    @Override
    protected void setUp() {
        showOffersFragment();
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_offer:
                        showOffersFragment();
                        return true;
                    case R.id.navigation_profile:
                        showProfileFragment();
                        return true;
                }
                return false;
            }
        });
    }



}
