package com.example.walker.ui.registry;

import androidx.annotation.NonNull;

import com.example.walker.model.User;
import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RegistryPresenter <V extends RegistryMvpView> extends BasePresenter<V> implements RegistryMvpPresenter<V> {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    @Inject
    public RegistryPresenter(SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onUserCreate(String email, String password, User user) {

        getMvpView().showLoading();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (!isViewAttached()) {
                                return;
                            }

                            db.collection("users").document(task.getResult().getUser().getUid()).set(user);
                            getMvpView().hideLoading();
                            getMvpView().openMainActivity();
                        }else{
                            getMvpView().hideLoading();
                            getMvpView().onError("Registry Failed.");
                        }
                    }
                });
    }
}
