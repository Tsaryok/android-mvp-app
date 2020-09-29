package com.example.walker.ui.search;

import com.example.walker.di.PerActivity;
import com.example.walker.ui.base.MvpPresenter;

@PerActivity
public interface SearchMvpPresenter<V extends SearchMvpView> extends MvpPresenter<V> {

    void onGettingAddresses(String addName);

}
