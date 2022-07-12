package com.example.producta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class AllProductsFragment extends Fragment {

//    private RecyclerView recyclerView;
//    private View allProductsView;
//    private DatabaseReference productsRef;
//    private FirebaseAuth mAuth;
//    private FirebaseUser firebaseUser;
//    private String uid;
//
//    public AllProductsFragment()
//    {
//
//    }
//
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        allProductsView = inflater.inflate(R.layout.fragment_uploadnew,container,false);
//
//        recyclerView = allProductsView.findViewById(R.id.allProductsRecycledView);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        mAuth = FirebaseAuth.getInstance();
//        firebaseUser = mAuth.getCurrentUser();
//
//        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
//
//        return allProductsView;
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<AllProducts> option = new
//                FirebaseRecyclerOptions.Builder<AllProducts>()
//                .setQuery(productsRef,AllProducts.class)
//                .build();
//
//        FirebaseRecyclerAdapter<AllProducts, AllProductsViewHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<AllProducts, AllProductsViewHolder>(option) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull AllProductsViewHolder holder, int position, @NonNull @NotNull AllProducts model) {
//                        productsRef.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                                if(snapshot.exists())
//                                {
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                            }
//                        });
//
//
//                    }
//
//                    @NonNull
//                    @NotNull
//                    @Override
//                    public AllProductsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//                        return null;
//                    }
//                };
//    }
//    public static class AllProductsViewHolder extends RecyclerView.ViewHolder {
//
//        TextView name,price;
//
//        public AllProductsViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            name = itemView.findViewById(R.id.productNameItemLayout);
//            price = itemView.findViewById(R.id.priceItemLayout);
//
//
//        }
//    }
}
