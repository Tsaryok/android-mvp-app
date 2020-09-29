package com.example.walker.ui.list;

import com.example.walker.ui.base.MvpPresenter;

public interface StoreListMvpPresenter<V extends StoreListMvpView> extends MvpPresenter<V> {

    void onSetStoreListAdapter();
}
