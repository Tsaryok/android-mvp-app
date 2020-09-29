package com.example.walker.ui.search;

import android.os.ResultReceiver;

import com.example.walker.ui.base.MvpView;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;

public interface SearchMvpView extends MvpView {

    void findAddressByName(String name);

    void startAddressesByNameIntentService(ResultReceiver addressResultReceiver, String addName);

    void setSearchAdapter(List<String> placeIds, List<String> placeNames);

    PlacesClient getPlacesClient();

}
