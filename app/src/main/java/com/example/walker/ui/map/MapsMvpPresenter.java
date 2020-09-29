package com.example.walker.ui.map;

import com.example.walker.ui.base.MvpPresenter;
import com.google.android.gms.maps.model.Marker;

public interface MapsMvpPresenter<V extends MapsMvpView> extends MvpPresenter<V> {

    void onGettingStores();

    void onMarkerClick(Marker marker);

}
