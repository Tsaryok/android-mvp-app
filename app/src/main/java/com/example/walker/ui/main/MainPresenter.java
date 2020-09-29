package com.example.walker.ui.main;

import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

}
