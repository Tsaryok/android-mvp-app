package com.example.walker.ui.offers;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;
import com.example.walker.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {
    OffersMvpView mvpView;
    private List<Product> products;

    private FirebaseStorage storage;


    public class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nameView;
        TextView descriptionView;
        ImageView imageView;
        TextView priceView;
        TextView discountView;

        public OfferViewHolder(View v) {
            super(v);
            nameView = (TextView) v.findViewById(R.id.offer_name_view);
            descriptionView = (TextView) v.findViewById(R.id.offer_descript_view);
            imageView = (ImageView) v.findViewById(R.id.offer_image);
            priceView = (TextView) v.findViewById(R.id.offer_price_view);
            discountView = (TextView) v.findViewById(R.id.offer_disc_view);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mvpView.showProductActivity(products.get(getAdapterPosition()).getId());
        }
    }

    public OfferAdapter(OffersFragment fragment, List<Product> products) {
        this.mvpView = fragment;
        this.products = products;

        storage = FirebaseStorage.getInstance();
    }

    @NotNull
    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false);
        return new OfferViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, int position) {

        holder.nameView.setText(products.get(position).getName());
        holder.descriptionView.setText(products.get(position).getDescription());
        holder.priceView.setText(String.valueOf(products.get(position).getPrice()));
        holder.discountView.setText(String.valueOf(products.get(position).getDiscount()));

        storage.getReferenceFromUrl(products.get(position).getImageUrl()).getDownloadUrl()
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
        return products.size();
    }
}
