package com.example.walker.ui.map;

import androidx.annotation.NonNull;

import com.example.walker.model.Store;
import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MapsPresenter <V extends MapsMvpView> extends BasePresenter<V> implements MapsMvpPresenter<V> {

    private FirebaseFirestore db;

    @Inject
    public MapsPresenter(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onGettingStores() {
        db.collection("stores").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (isViewAttached()) {
                        List<Store> stores = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Store store = document.toObject(Store.class);
                            store.setId(document.getId());
                            stores.add(store);
                        }
                        getMvpView().setStoreList(stores);
                    }
                }
            }
        });
    }

    @Override
    public void onMarkerClick(Marker marker) {
        db.collection("stores").document(marker.getTag().toString()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (isViewAttached()) {
                        Store store = task.getResult().toObject(Store.class);
                        store.setId(task.getResult().getId());
                        getMvpView().showStoreInformation(marker, store);
                    }
                }
            }
        });
    }


}
