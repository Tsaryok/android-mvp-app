package com.example.walker.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;
import com.example.walker.di.component.ActivityComponent;
import com.example.walker.model.Product;
import com.example.walker.model.Store;
import com.example.walker.ui.base.BaseFragment;
import com.example.walker.ui.product.ProductActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreListFragment extends BaseFragment implements StoreListMvpView {

    private static final int FILTER_REQUEST_CODE = 2;

    public static final String TAG = "StoreListFragment";

    @Inject
    StoreListMvpPresenter<StoreListMvpView> mPresenter;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    private StoreListAdapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;

    public static StoreListFragment newInstance() {
        Bundle args = new Bundle();
        StoreListFragment fragment = new StoreListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        setUp(view);

        return view;
    }

    @Override
    protected void setUp(View view) {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mPresenter.onSetStoreListAdapter();
        /*filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = FilterActivity.getStartIntent(getActivity());
                startActivityForResult(i, FILTER_REQUEST_CODE);
            }
        });*/
    }

    @Override
    public void showStoreActivity(String storeId) {
        Intent i = ProductActivity.getStartIntent(this.getBaseActivity());
        i.putExtra("storeId", storeId);
        startActivity(i);
    }

    @Override
    public void setStoreAdapter(List<Store> stores) {
        mAdapter = new StoreListAdapter(this, stores);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showProductActivity(String productId){
        Intent i = ProductActivity.getStartIntent(this.getBaseActivity());
        i.putExtra("productId", productId);
        startActivity(i);
    }

    @Override
    public void setProductAdapter(List<Product> products) {
        //mAdapter = new StoreListAdapter(this, products);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
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
                    //showFollowingStoreMarkers(filterStores(radius, tags));
                    break;
            }
        }
    }
}
