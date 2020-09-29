package com.example.walker.ui.map;

import android.location.Address;

import com.example.walker.model.Store;
import com.example.walker.ui.base.MvpView;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public interface MapsMvpView extends MvpView {

    //List<Store> filterStores(float radius, List<String> tags, List<Store> stores);

    void showFollowingStoreMarkers(List<Store> stores);

    void setMarkerByAddress(Marker marker, Address address);

    void showStoreInformation(Marker marker, Store store);

    void hideStoreInformation();

    void setStoreList(List<Store> storeList);
}