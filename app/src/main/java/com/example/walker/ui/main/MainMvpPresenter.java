package com.example.walker.ui.main;

import com.example.walker.di.PerActivity;
import com.example.walker.ui.base.MvpPresenter;



@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

}
