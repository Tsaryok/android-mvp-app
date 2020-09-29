package com.example.walker.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{
    private ProfileMvpView mvpView;
    private List<String> profileList;

    public class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;
        ImageView mImageView;

        public ProfileViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.item_profile_tv);
            mImageView = (ImageView) v.findViewById(R.id.item_profile_image);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }

    }

    public ProfileAdapter(ProfileFragment fragment, List<String> profileList) {
        this.mvpView = fragment;
        this.profileList = profileList;
    }

    @NotNull
    @Override
    public ProfileAdapter.ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile, parent, false);
        return new ProfileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileAdapter.ProfileViewHolder holder, int position) {
        holder.mTextView.setText(mvpView.getResourceId("title_" + profileList.get(position), "string"));
        holder.mImageView.setImageResource(mvpView.getResourceId("ic_" + profileList.get(position), "mipmap"));
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}
