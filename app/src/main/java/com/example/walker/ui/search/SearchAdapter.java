package com.example.walker.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    SearchMvpView mvpView;

    private List<String> addresses;

    public static class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView nameView;
        public SearchViewHolder(View v) {
            super(v);
            nameView = (TextView) v.findViewById(R.id.search_address_tv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //mvpView.showProductActivity(getAdapterPosition());
        }
    }

    public SearchAdapter(SearchActivity activity, List<String> addresses) {
        this.mvpView = activity;
        this.addresses = addresses;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, int position) {
        holder.nameView.setText(addresses.get(position));
    }
    @Override
    public int getItemCount() {
        return addresses.size();
    }
}
