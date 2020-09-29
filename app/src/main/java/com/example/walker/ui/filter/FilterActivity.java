package com.example.walker.ui.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;
import com.example.walker.ui.base.BaseActivity;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends BaseActivity implements FilterMvpView {

    @BindView(R.id.filter_toolbar)
    Toolbar toolbar;

    @BindView(R.id.filter_tags_list)
    RecyclerView recyclerView;

    @BindView(R.id.filter_radius_et)
    EditText radiusEditText;

    @BindView(R.id.filter_radius_rb)
    RatingBar ratingBar;

    @BindView(R.id.filter_save_btn)
    Button saveButton;

    @Inject
    FilterMvpPresenter<FilterMvpView> mPresenter;

    private TagsAdapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;

    List<String> tags;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, FilterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        tags = Arrays.asList(getResources().getStringArray(R.array.store_tags));

        mAdapter = new TagsAdapter(this, tags);
        recyclerView.setAdapter(mAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("radius", Integer.parseInt(radiusEditText.getText().toString()));
                intent.putExtra("rating", ratingBar.getRating());
                intent.putExtra("tags", new Gson().toJson(mAdapter.getSelectedTags()));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public String getStringResource(String name){
        return getString(getResources().getIdentifier(name, "string", getPackageName()));
    }
}