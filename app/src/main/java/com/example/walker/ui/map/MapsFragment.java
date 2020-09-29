package com.example.walker.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.walker.R;
import com.example.walker.di.component.ActivityComponent;
import com.example.walker.model.Store;
import com.example.walker.ui.base.BaseFragment;
import com.example.walker.ui.store.StoreActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsFragment  extends BaseFragment implements OnMapReadyCallback, MapsMvpView {

    @Inject
    MapsMvpPresenter<MapsMvpView> mPresenter;

    private GoogleMap mMap;
    private Geocoder mGeocoder;

    @BindView(R.id.first_info)
    CardView firstInfo;

    /*@BindView(R.id.search_view)
    CardView searchView;*/

    @BindView(R.id.first_info_title)
    TextView firstInfoTitle;

    @BindView(R.id.first_info_description)
    TextView firstInfoDescription;

    @BindView(R.id.map_view)
    MapView mapView;

    //private LocationManager mLocationManager;
    //private MyLocationListener mLocationListener;
    private LatLng myLatLng;
    private Marker currentMarker;

    private FusedLocationProviderClient fusedLocationClient;

    private List<Store> stores;
    private List<Marker> storeMarkers;
    private String currentStoreId;

    public static final String TAG = "MapsFragment";

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final int FILTER_REQUEST_CODE = 2;

    private static final int AUTO_COMP_REQ_CODE = 3;

    public static MapsFragment newInstance() {
        Bundle args = new Bundle();
        MapsFragment fragment = new MapsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        //mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        setUp(view);
        return view;
    }

    @Override
    protected void setUp(View view) {
        firstInfo.setOnClickListener(new MapsFragment.ClickListener());
        //searchView.setOnClickListener(new MapsFragment.ClickListener());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mapView.onDestroy();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mGeocoder = new Geocoder(getContext(), Locale.getDefault());

        mMap.setOnMarkerClickListener(new MapsFragment.MarkerClickListener());
        mMap.setOnMapClickListener(new MapsFragment.MapClickListener());

        enableMyLocationIfPermitted();

        storeMarkers = new ArrayList<>();
        mPresenter.onGettingStores();
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this.getBaseActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getBaseActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                break;
            }

        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this.getBaseActivity(), "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng minsk = new LatLng(53.9, 27.56667);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minsk, 10));
        myLatLng = minsk;
    }

    @Override
    public void setStoreList(List<Store> stores) {
        this.stores = stores;
        updateStoreMarkers();
    }

    private void updateStoreMarkers(){
        try {
            storeMarkers.clear();
            for (Store store : stores) {
                Address address = mGeocoder
                        .getFromLocation(store.getGeoPoint().getLatitude(),
                                store.getGeoPoint().getLongitude(), 3).get(1);
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(store.getGeoPoint().getLatitude(),
                                store.getGeoPoint().getLongitude()))
                        .title(address.getAddressLine(0))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                marker.setTag(store.getId());
                storeMarkers.add(marker);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMarkerByAddress(Marker marker, Address address){
        marker.setPosition(new LatLng(address.getLatitude(), address.getLongitude()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));
    }

    public List<Store> filterStores(int radius, List<String> tags){
        List<Store> filteredStores = stores;
        if (radius != 0) {
            filteredStores = filterStoresByRadius(filteredStores, radius);
        }
        if (!tags.isEmpty()) {
            filteredStores = filterStoresByTags(filteredStores, tags);
        }
        return filteredStores;
    }

    private List<Store> filterStoresByTags(List<Store> stores, List<String> tags) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Stream<Store> storesStream = stores.parallelStream();
            return storesStream.filter(store -> store.getTags().containsAll(tags)).collect(Collectors.toCollection(ArrayList::new));
        } else {
            List<Store> filteredStores = new ArrayList<>();
            for (Store store : stores){
                if (store.getTags().containsAll(tags)) {
                    filteredStores.add(store);
                }
            }
            return filteredStores;
        }
    }

    private List<Store> filterStoresByRadius(List<Store> stores, int radius) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Stream<Store> storesStream = stores.parallelStream();
            return storesStream.filter(store -> isDistance(store, radius)).collect(Collectors.toCollection(ArrayList::new));
        }else {
            List<Store> filteredStores = new ArrayList<>();
            for (Store store : stores){
                if (isDistance(store, radius)) {
                    filteredStores.add(store);
                }
            }
            return filteredStores;
        }
    }

    private boolean isDistance(Store store, int radius){
        float[] result = { 0 };
        Location.distanceBetween(mMap.getCameraPosition().target.latitude,
                mMap.getCameraPosition().target.longitude,
                store.getGeoPoint().getLatitude(),
                store.getGeoPoint().getLongitude(), result);
        return result[0] < radius;
    }

    @Override
    public void showFollowingStoreMarkers(List<Store> stores){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            storeMarkers.parallelStream()
                    .filter(marker -> stores.parallelStream().noneMatch(store -> store.getId().equals((String) marker.getTag())))
                    .forEach(marker -> marker.setVisible(false));
        }else{
            for (Marker marker : storeMarkers){
                for (Store store : stores){
                    if (store.getId().equals((String) marker.getTag())){
                        break;
                    }
                    marker.setVisible(false);
                }
            }
        }
    }

    @Override
    public void showStoreInformation(Marker marker, Store store) {
        if (marker == currentMarker)
        {
            hideStoreInformation();
        }else {
            currentStoreId = store.getId();
            firstInfoTitle.setText(store.getName());
            firstInfoDescription.setText(store.getDescription());
            firstInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideStoreInformation() {
        firstInfo.setVisibility(View.INVISIBLE);
    }

    private class MarkerClickListener implements GoogleMap.OnMarkerClickListener{
        @Override
        public boolean onMarkerClick(Marker marker) {
            mPresenter.onMarkerClick(marker);
            return false;
        }
    }

    private class MapClickListener implements GoogleMap.OnMapClickListener{
        @Override
        public void onMapClick(LatLng latLng) {
            hideStoreInformation();
        }
    }

    private class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.first_info:
                    if (currentStoreId != null) {
                        Intent i = StoreActivity.getStartIntent(getActivity());
                        i.putExtra("storeId", currentStoreId);
                        startActivity(i);
                    }else {
                        onError("Current store id is null.");
                    }
                    break;
                /*case R.id.search_view: {
                    Intent i = SearchActivity.getStartIntent(getActivity());
                    startActivity(i);
                    break;
                }*/
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode){
                case FILTER_REQUEST_CODE:
                    int radius = data.getIntExtra("radius", 0);
                    float rating = data.getFloatExtra("rating", 0);
                    List<String> tags = new Gson().fromJson(data.getStringExtra("tags"),
                            new TypeToken<List<String>>() {}.getType());
                    onError("Set radius: " + String.valueOf(radius) + "\nSet tag list: " + tags.toString());
                    showFollowingStoreMarkers(filterStores(radius, tags));
                    break;
            }
        }
    }

}