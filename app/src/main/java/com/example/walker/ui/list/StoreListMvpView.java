package com.example.walker.ui.list;

import com.example.walker.model.Product;
import com.example.walker.model.Store;
import com.example.walker.ui.base.MvpView;

import java.util.List;

public interface StoreListMvpView extends MvpView {

    void showStoreActivity(String storeId);

    void setStoreAdapter(List<Store> stores);

    void showProductActivity(String productId);

    void setProductAdapter(List<Product> products);
}
