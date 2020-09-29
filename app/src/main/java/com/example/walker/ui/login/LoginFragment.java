package com.example.walker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.walker.R;
import com.example.walker.di.component.ActivityComponent;
import com.example.walker.ui.base.BaseFragment;
import com.example.walker.ui.main.MainActivity;
import com.example.walker.ui.registry.RegistryFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginMvpView {

    public static final String TAG = "LoginFragment";
    private final int RC_SIGN_IN = 123;

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.login_et_email)
    EditText mEmailEditText;

    @BindView(R.id.login_et_password)
    EditText mPasswordEditText;

    GoogleSignInClient mGoogleSignInClient;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onSignIn();
    }

    @Override
    protected void setUp(View view) {

    }

    @OnClick(R.id.btn_login)
    void onLoginClick(View v) {
        mPresenter.onLoginClick(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString());
    }

    @OnClick(R.id.ib_google_login)
    void onGoogleLoginClick(View v) {
        signInGoogle();
    }

    @OnClick(R.id.ib_fb_login)
    void onFbLoginClick(View v) {
        mPresenter.onFacebookLoginClick();
    }

    @OnClick(R.id.btn_register)
    void onRegisterClick(View v) {
        showRegistryFragment();
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                mPresenter.firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(getContext());
        startActivity(intent);
        getBaseActivity().onFragmentDetached(TAG);

    }

    @Override
    public String getStringById(int id) {
        return getString(id);
    }

    public void showRegistryFragment() {
        getBaseActivity().getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(R.anim.card_flip_right_enter,
                R.anim.card_flip_right_exit,
                R.anim.card_flip_left_enter,
                R.anim.card_flip_left_exit)
                .replace(R.id.cl_root_view, RegistryFragment.newInstance(), RegistryFragment.TAG)
                .addToBackStack(null)
                .commit();
    }



    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
