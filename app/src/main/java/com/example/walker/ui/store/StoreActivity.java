package com.example.walker.ui.store;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;
import com.example.walker.model.Product;
import com.example.walker.model.Store;
import com.example.walker.ui.base.BaseActivity;
import com.example.walker.ui.product.ProductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreActivity extends BaseActivity implements StoreMvpView {

    @Inject
    StoreMvpPresenter<StoreMvpView> mPresenter;

    private Store store;

    private List<Product> products;

    @BindView(R.id.store_appbar)
    CollapsingToolbarLayout appbar;

    @BindView(R.id.store_toolbar)
    Toolbar toolbar;

    @BindView(R.id.product_list)
    RecyclerView recyclerView;

    @BindView(R.id.app_bar_image)
    ImageView appBarImage;

    private ProductAdapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;

    private FirebaseStorage  storage;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, StoreActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

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
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        storage = FirebaseStorage.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("storeId") != null) {
                mPresenter.onStoreLoad(bundle.getString("storeId"));
            }else {
                onError("Returned string is null");
            }
        }else{
            onError("Bundle is null");
        }
    }

    @Override
    public void loadStore(Store store) {
        this.store = store;
        appbar.setTitle(store.getName());
        storage.getReferenceFromUrl(store.getImageUrl()).getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Picasso.get().load(task.getResult()).into(appBarImage);
                        }
                    }
                });
        mPresenter.onProductListLoad(store.getId());
    }

    private class BackClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public void showProductActivity(int position){
        Intent i = ProductActivity.getStartIntent(this);
        i.putExtra("productId", products.get(position).getId());
        startActivity(i);
    }

    @Override
    public void setProductAdapter(List<Product> products) {
        this.products = products;
        mAdapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(mAdapter);
    }
}
