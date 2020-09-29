package com.example.walker.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.walker.R;
import com.example.walker.model.Product;
import com.example.walker.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductActivity extends BaseActivity implements ProductMvpView {

    @Inject
    ProductMvpPresenter<ProductMvpView> mPresenter;

    @BindView(R.id.product_toolbar)
    Toolbar toolbar;

    @BindView(R.id.product_description_tv)
    TextView mDescriptionTextView;

    @BindView(R.id.product_number_tv)
    TextView mNumberTextView;

    @BindView(R.id.product_discount_tv)
    TextView mDiscountTextView;

    @BindView(R.id.product_disc_price_tv)
    TextView mDiscountedPriceTextView;

    @BindView(R.id.product_price_tv)
    TextView mPriceTextView;

    @BindView(R.id.product_date_tv)
    TextView mDateTextView;

    private Product product;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProductActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();

    }

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new BackClickListener());
        String productId = getIntent().getStringExtra("productId");
        if (!productId.isEmpty()) {
            mPresenter.onProductLoad(productId);
        }else{
            onError("null.");
        }
    }

    @Override
    public void loadProduct(Product product) {
        this.product = product;
        //onError(product.getName() + " " + product.getDescription());
        if (product != null) {
            toolbar.setTitle(product.getName());
            mDescriptionTextView.setText(product.getDescription());
            mNumberTextView.setText(String.valueOf(product.getNumber()));
            mDiscountTextView.setText(String.valueOf(product.getDiscount()));
            mDiscountedPriceTextView.setText(String.valueOf(product.getDiscountedPrice()));
            mPriceTextView.setText(String.valueOf(product.getPrice()));
            mDateTextView.setText(product.getDate().toDate().toString());
        }else{
            onError("Product is null.");
        }
    }

    private class BackClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
