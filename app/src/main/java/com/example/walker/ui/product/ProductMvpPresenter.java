package com.example.walker.ui.product;

import com.example.walker.di.PerActivity;
import com.example.walker.ui.base.MvpPresenter;

@PerActivity
public interface ProductMvpPresenter<V extends ProductMvpView> extends MvpPresenter<V> {

    void onProductLoad(String productId);
}
