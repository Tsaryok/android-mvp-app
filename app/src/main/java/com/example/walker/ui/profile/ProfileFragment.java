package com.example.walker.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;
import com.example.walker.di.component.ActivityComponent;
import com.example.walker.ui.base.BaseFragment;
import com.example.walker.ui.offers.OfferAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends BaseFragment implements ProfileMvpView {

    public static final String TAG = "ProfileFragment";

    @Inject
    ProfileMvpPresenter<ProfileMvpView> mPresenter;

    @BindView(R.id.list_profile)
    RecyclerView recyclerView;

    private ProfileAdapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

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
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        List<String> profileList = Arrays.asList(getResources().getStringArray(R.array.profile_items));

        mAdapter = new ProfileAdapter(this, profileList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getResourceId(String name, String resourceName){
        return getResources().getIdentifier(name, resourceName, getActivity().getPackageName());
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
