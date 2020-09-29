package com.example.walker.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.walker.R;
import com.example.walker.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        showLoginFragment();
    }

    public void showLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.cl_root_view, LoginFragment.newInstance(), LoginFragment.TAG)
                .commit();
    }

    @Override
    protected void setUp() {

    }
}
