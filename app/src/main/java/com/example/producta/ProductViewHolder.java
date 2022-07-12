package com.example.producta;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView productName, productPrice;
    public ImageView productImage;
    public itemClickListener itemClickListener;

    public ProductViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.productImage);
        productName = itemView.findViewById(R.id.productNameItemLayout);
        productPrice = itemView.findViewById(R.id.priceItemLayout);
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
