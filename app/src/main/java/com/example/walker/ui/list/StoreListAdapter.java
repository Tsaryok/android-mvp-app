package com.example.walker.ui.list;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;
import com.example.walker.model.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreListViewHolder> {
    StoreListMvpView mvpView;
    private List<Store> stores;

    private FirebaseStorage storage;

    public class StoreListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nameView;
        TextView descriptionView;
        ImageView imageView;

        public StoreListViewHolder(View v) {
            super(v);
            nameView = (TextView) v.findViewById(R.id.store_name_tv);
            descriptionView = (TextView) v.findViewById(R.id.store_description_tv);
            imageView = (ImageView) v.findViewById(R.id.store_image);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mvpView.showStoreActivity(stores.get(getAdapterPosition()).getId());
        }
    }

    public StoreListAdapter(StoreListFragment fragment, List<Store> stores) {
        this.mvpView = fragment;
        this.stores = stores;

        storage = FirebaseStorage.getInstance();
    }

    @NotNull
    @Override
    public StoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store, parent, false);
        return new StoreListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StoreListViewHolder holder, int position) {

        holder.nameView.setText(stores.get(position).getName());
        holder.descriptionView.setText(stores.get(position).getDescription());

        storage.getReferenceFromUrl(stores.get(position).getImageUrl()).getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Picasso.get().load(task.getResult()).resize(250,250).centerCrop().into(holder.imageView);
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }
}
