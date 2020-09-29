package com.example.walker.ui.filter;

import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class FilterPresenter <V extends FilterMvpView> extends BasePresenter<V> implements FilterMvpPresenter<V> {

    @Inject
    public FilterPresenter(SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }
}
