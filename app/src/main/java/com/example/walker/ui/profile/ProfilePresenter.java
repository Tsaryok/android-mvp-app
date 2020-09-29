package com.example.walker.ui.profile;

import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ProfilePresenter<V extends ProfileMvpView> extends BasePresenter<V> implements ProfileMvpPresenter<V>  {

    @Inject
    public ProfilePresenter(SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

}
