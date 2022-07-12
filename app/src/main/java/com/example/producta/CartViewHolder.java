package com.example.producta;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productName, productPrice;
    public ImageView productImage;
    private itemClickListener itemClickListener;

    public CartViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        productName = itemView.findViewById(R.id.checkOutProductName);
        productPrice = itemView.findViewById(R.id.checkOutProductPrice);
        productImage = itemView.findViewById(R.id.checkOutProductImage);
    }

    @Override
    public void onClick(View view) {

       itemClickListener.onClick(view, getAdapterPosition(), false);

    }

    public void setItemClickListener(itemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
