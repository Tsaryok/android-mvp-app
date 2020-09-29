package com.example.walker.ui.list;

import androidx.annotation.NonNull;

import com.example.walker.model.Store;
import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class StoreListPresenter<V extends StoreListMvpView> extends BasePresenter<V> implements StoreListMvpPresenter<V> {

    private FirebaseFirestore db;

    @Inject
    public StoreListPresenter(SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onSetStoreListAdapter() {

        db.collection("stores").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (isViewAttached()) {
                                List<Store> storeList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Store store = document.toObject(Store.class);
                                    store.setId(document.getId());
                                    storeList.add(store);
                                }
                                getMvpView().setStoreAdapter(storeList);
                            }
                        }
                    }
                });
    }
}
