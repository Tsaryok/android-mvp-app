package com.example.walker.ui.store;

import com.example.walker.di.PerActivity;
import com.example.walker.ui.base.MvpPresenter;

@PerActivity
public interface StoreMvpPresenter<V extends StoreMvpView> extends MvpPresenter<V> {

    void onStoreLoad(String storeId);

    void onProductListLoad(String storeId);

}
