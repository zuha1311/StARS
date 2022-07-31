package com.example.producta;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class AllProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView productName, productPrice, productRentStatus;
    public ImageView productImage;
    public itemClickListener itemClickListener;

    public AllProductsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.allProducts_productImage);
        productName = itemView.findViewById(R.id.allProducts_productNameItemLayout);
        productPrice = itemView.findViewById(R.id.allProducts_priceItemLayout);
        productRentStatus = itemView.findViewById(R.id.allProducts_rentStatusItemLayout);
    }

    public void setItemClickListener(itemClickListener listner)
    {
        this.itemClickListener = listner;
    }




    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);

    }
}
