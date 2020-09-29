package com.example.walker.ui.offers;

import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class OffersPresenter<V extends OffersMvpView> extends BasePresenter<V> implements OffersMvpPresenter<V> {

    @Inject
    public OffersPresenter(SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

}
