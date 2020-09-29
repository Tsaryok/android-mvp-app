package com.example.walker.ui.store;

import com.example.walker.model.Product;
import com.example.walker.model.Store;
import com.example.walker.ui.base.MvpView;

import java.util.List;

public interface StoreMvpView extends MvpView {

    void loadStore(Store store);

    void showProductActivity(int position);

    void setProductAdapter(List<Product> products);

}
