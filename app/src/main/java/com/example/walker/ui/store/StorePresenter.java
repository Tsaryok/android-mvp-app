package com.example.walker.ui.store;

import androidx.annotation.NonNull;

import com.example.walker.model.Product;
import com.example.walker.model.Store;
import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;
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

public class StorePresenter <V extends StoreMvpView> extends BasePresenter<V> implements StoreMvpPresenter<V> {

    private FirebaseFirestore db;

    @Inject
    public StorePresenter(SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onStoreLoad(String storeId) {

        db.collection("stores").document(storeId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (isViewAttached()) {
                                Store store = task.getResult().toObject(Store.class);
                                store.setId(task.getResult().getId());
                                getMvpView().loadStore(store);
                            }
                        }
                    }
                });
    }

    @Override
    public void onProductListLoad(String storeId) {

        db.collection("products").whereEqualTo("storeId", storeId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (isViewAttached()) {
                                List<Product> productList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    product.setId(document.getId());
                                    productList.add(product);
                                }
                                getMvpView().setProductAdapter(productList);
                            }
                        }
                    }
                });
    }
}
