package com.example.walker.ui.product;

import androidx.annotation.NonNull;

import com.example.walker.model.Product;
import com.example.walker.ui.base.BasePresenter;
import com.example.walker.utils.rx.SchedulerProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ProductPresenter<V extends ProductMvpView> extends BasePresenter<V> implements ProductMvpPresenter<V> {

    private FirebaseFirestore db;

    @Inject
    public ProductPresenter(SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onProductLoad(String productId) {
        db.collection("products").document(productId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (isViewAttached()) {
                                Product product = task.getResult().toObject(Product.class);
                                product.setId(task.getResult().getId());
                                getMvpView().loadProduct(product);
                            }
                        }
                    }
                });
    }
}
