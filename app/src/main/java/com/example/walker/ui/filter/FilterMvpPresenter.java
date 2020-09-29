package com.example.walker.ui.filter;

import com.example.walker.di.PerActivity;
import com.example.walker.ui.base.MvpPresenter;

@PerActivity
public interface FilterMvpPresenter<V extends FilterMvpView> extends MvpPresenter<V> {

}