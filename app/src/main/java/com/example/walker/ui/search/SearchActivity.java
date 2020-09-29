package com.example.walker.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;
import com.example.walker.service.AddressesByNameIntentService;
import com.example.walker.ui.base.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements SearchMvpView {

    @BindView(R.id.search_sv)
    SearchView searchView;

    @BindView(R.id.search_rv)
    RecyclerView recyclerView;

    @Inject
    SearchMvpPresenter<SearchMvpView> mPresenter;

    private SearchAdapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;

    List<String> placeIds;

    PlacesClient placesClient;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //set search view focused

        searchView.setOnQueryTextListener(onQueryTextListener);

        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(SearchActivity.this, apiKey);
        }
        placesClient = Places.createClient(SearchActivity.this);
    }

    @Override
    public void findAddressByName(String name) {
        mPresenter.onGettingAddresses(name);
    }

    @Override
    public void startAddressesByNameIntentService(ResultReceiver addressResultReceiver, String addName) {
        Intent intent = new Intent(this, AddressesByNameIntentService.class);
        intent.putExtra("address_receiver", addressResultReceiver);
        intent.putExtra("address_name", addName);
        startService(intent);
    }

    @Override
    public void setSearchAdapter(List<String> placeIds, List<String> placeNames) {
        this.placeIds = placeIds;
        mAdapter = new SearchAdapter(this, placeNames);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public PlacesClient getPlacesClient() {
        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(this, apiKey);
        }
        return Places.createClient(this);
    }

    private class BackClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener(){

        @Override
        public boolean onQueryTextSubmit(String query) {
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
            FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                    .setCountry("BY")
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .setSessionToken(token)
                    .setQuery(newText)
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
                        setSearchAdapter(placeIds, placeNames);
                    }else{
                        onError("Task is unsuccessful.");
                    }
                }
            });
            return true;
        }
    };
}
