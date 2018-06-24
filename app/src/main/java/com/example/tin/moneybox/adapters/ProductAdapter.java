package com.example.tin.moneybox.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tin.moneybox.ProductPositionListener;
import com.example.tin.moneybox.R;
import com.example.tin.moneybox.serverConnection.response.ProductResponse;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<ProductResponse.ProductModel> mProducts;
    private final Context context;

    private ProductPositionListener productPositionListener;

    /* Constructor:
     * Pass in the StationPositionListener Interface into the Adapter on construction */
    public ProductAdapter(ArrayList<ProductResponse.ProductModel> products, Context context, ProductPositionListener listener) {
        this.mProducts = products;
        this.context = context;
        this.productPositionListener = listener;

    }

    // We are passing the station data via a method, not when the Adapter is created
    public void setProducts(ArrayList<ProductResponse.ProductModel> products) {
        this.mProducts = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        ProductResponse.ProductModel product = mProducts.get(position);

        viewHolder.btnProduct.setText(product.Product.FriendlyName);
    }

    @Override
    public int getItemCount() {
        if (mProducts == null) {
            return 0;
        } else {
            return mProducts.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final Button btnProduct;

        public ViewHolder(View itemView) {
            super(itemView);

            btnProduct = itemView.findViewById(R.id.btn_Product);

            /* Setting up the onClickListeners */
            btnProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* Implementing the interface method */
                    productPositionListener.btnProductClick(view, getAdapterPosition());
                }
            });
        }
    }
}
