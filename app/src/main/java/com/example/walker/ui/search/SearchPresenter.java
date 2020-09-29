package com.example.walker.ui.search;

import androidx.annotation.NonNull;

import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchPresenter <V extends SearchMvpView> extends BasePresenter<V> implements SearchMvpPresenter<V> {

    PlacesClient placesClient;


    @Inject
    public SearchPresenter(SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        placesClient = getMvpView().getPlacesClient();
    }

    @Override
    public void onGettingAddresses(String addName) {
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setCountry("BY")
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(addName)
                .build();

        placesClient.findAutocompletePredictions(request).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
            @Override
            public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                if (task.isSuccessful()) {
                    List<String> placeIds = new ArrayList<>();
                    List<String> placeNames = new ArrayList<>();
                    for (AutocompletePrediction prediction : task.getResult().getAutocompletePredictions()) {
                        placeIds.add(prediction.getPlaceId());
                        placeNames.add(prediction.getPrimaryText(null).toString());
                    }
                    getMvpView().setSearchAdapter(placeIds, placeNames);
                }else{
                    getMvpView().onError("Task is unsuccessful.");
                }
            }
        });
    }

}
