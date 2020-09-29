package com.example.walker.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.walker.R;
import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.CommonUtils;
import com.example.walker.utils.rx.SchedulerProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    private static final String TAG = "LoginPresenter";

    FirebaseAuth firebaseAuth;

    @Inject
    public LoginPresenter(SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onLoginClick(String email, String password) {
        //validate email and password
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        getMvpView().showLoading();

        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (!isViewAttached()) {
                                return;
                            }
                            getMvpView().hideLoading();
                            getMvpView().openMainActivity();
                        }else{
                            getMvpView().hideLoading();
                            getMvpView().onError("Authentication Failed.");
                        }
                    }
                });
    }

    @Override
    public void onFacebookLoginClick() {
        getMvpView().showLoading();
        getMvpView().hideLoading();
    }

    @Override
    public void onSignIn() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            getMvpView().openMainActivity();
        }
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            getMvpView().openMainActivity();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            getMvpView().onError("Authentication Failed.");
                        }

                    }
                });
    }
}
