package com.example.walker.ui.store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.walker.R;
import com.example.walker.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    StoreMvpView mvpView;
    private List<Product> products;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView nameView;
        public ProductViewHolder(View v) {
            super(v);
            nameView = (TextView) v.findViewById(R.id.product_name_view);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mvpView.showProductActivity(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductAdapter(StoreActivity activity, List<Product> products) {
        this.mvpView = activity;
        this.products = products;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameView.setText(products.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }
}
