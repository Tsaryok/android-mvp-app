package com.example.walker.ui.offers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.walker.R;
import com.example.walker.di.component.ActivityComponent;
import com.example.walker.ui.base.BaseFragment;
import com.example.walker.ui.list.StoreListFragment;
import com.example.walker.ui.map.MapsFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersFragment extends BaseFragment implements OffersMvpView {

    public static final String TAG = "OffersFragment";

    @Inject
    OffersMvpPresenter<OffersMvpView> mPresenter;

    @BindView(R.id.offers_tl)
    TabLayout tabLayout;

    public static OffersFragment newInstance() {
        Bundle args = new Bundle();
        OffersFragment fragment = new OffersFragment();
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
        showListFragment();
        //tabLayout.addOnTabSelectedListener();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    public void showListFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, StoreListFragment.newInstance(), StoreListFragment.TAG)
                .commit();
    }

    public void showMapsFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, MapsFragment.newInstance(), MapsFragment.TAG)
                .commit();
    }
}
