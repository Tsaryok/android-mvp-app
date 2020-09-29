package com.example.walker.ui.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {

    private FilterMvpView mvpView;

    private List<String> tags;

    private List<Boolean> selectedTags;

    public class TagsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public ToggleButton toggleButton;
        public TagsViewHolder(View v) {
            super(v);
            toggleButton = (ToggleButton)v.findViewById(R.id.tag_tb);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tag_tb:
                    if (toggleButton.isChecked()) {
                        selectedTags.set(getAdapterPosition(), true);
                    }else{
                        selectedTags.set(getAdapterPosition(), false);
                    }
                    break;
            }
        }
    }

    public TagsAdapter(FilterActivity activity, List<String> tags) {
        this.mvpView = activity;
        this.tags = tags;
        this.selectedTags = new ArrayList<>();
    }

    @Override
    public TagsAdapter.TagsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false);
        return new TagsAdapter.TagsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TagsAdapter.TagsViewHolder holder, int position) {
        holder.toggleButton.setTextOff(mvpView.getStringResource("title_" + tags.get(position)));
        holder.toggleButton.setTextOn(mvpView.getStringResource("title_" + tags.get(position)));
        selectedTags.add(position, false);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public List<String> getSelectedTags(){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++){
            if (selectedTags.get(i)){
                result.add(tags.get(i));
            }
        }
        return result;
    }
}
