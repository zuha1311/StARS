package com.example.producta.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producta.Controller.Products;
import com.example.producta.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;


public class AllProductsFragment extends Fragment {

    private RecyclerView recyclerView;
    private View allProductsView;
    private DatabaseReference productsRef;
    private FirebaseAuth mAuth;
    private String firebaseUser;
    private String uid;

    public AllProductsFragment()
    {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        allProductsView = inflater.inflate(R.layout.fragment_allproducts,container,false);

        recyclerView = allProductsView.findViewById(R.id.allProductsRecycledView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getUid();

        productsRef = FirebaseDatabase.getInstance().getReference().child("Products of user").child(firebaseUser);

        return allProductsView;

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> option = new
                FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(productsRef,Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, AllProductsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Products, AllProductsViewHolder>(option) {
                    @Override
                    protected void onBindViewHolder(@NonNull AllProductsViewHolder holder, int position, @NonNull @NotNull Products model) {
                        productsRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                   holder.name.setText(model.getName());
                                   holder.price.append(model.getRate());
                                   holder.productStatus.setText(model.getProductStatus());
                                   Picasso.get().load(model.getImage()).into(holder.itemImage);

                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                    }

                    @NonNull
                    @NotNull
                    @Override
                    public AllProductsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_products_item_layout,parent,false);
                        AllProductsViewHolder holder = new AllProductsViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }
    public static class AllProductsViewHolder extends RecyclerView.ViewHolder {

        TextView name,price, productStatus;
        ImageView itemImage;

        public AllProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.allProducts_productNameItemLayout);
            price = itemView.findViewById(R.id.allProducts_priceItemLayout);
            productStatus = itemView.findViewById(R.id.allProducts_rentStatusItemLayout);
            itemImage = itemView.findViewById(R.id.allProducts_productImage);


        }
    }
}
