package com.example.walker.ui.product;

import com.example.walker.model.Product;
import com.example.walker.ui.base.MvpView;

public interface ProductMvpView extends MvpView {

    void loadProduct(Product product);
}
