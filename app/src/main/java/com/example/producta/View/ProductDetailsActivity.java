package com.example.producta.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.producta.Controller.Products;
import com.example.producta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImage, backButton;
    private TextView productName, productPrice, productDescription, productPeriod;
    private Button addToCart;
    private String productID  = "";
    private FirebaseAuth mAuth;
    private String currentUser;
    private String link;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productImage = findViewById(R.id.productDetailsImage);
        productName = findViewById(R.id.productDetailsName);
        productPrice = findViewById(R.id.productDetailsPrice);
        productDescription = findViewById(R.id.productDetailsDescription);
        productPeriod = findViewById(R.id.productDetailsPeriod);
        addToCart = findViewById(R.id.addToCartButton);

        productID = getIntent().getStringExtra("pid");
        Toast.makeText(this, "Product ID"+productID, Toast.LENGTH_SHORT).show();

        backButton = findViewById(R.id.productDetailsBackBtn);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();

        Toast.makeText(this, "Current User ID: "+currentUser, Toast.LENGTH_SHORT).show();



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetailsActivity.super.onBackPressed();
            }
        });

        getProductDetails(productID);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();

            }
        });
    }

    private void addingToCartList() {

        String saveCurrentDate, saveCurrentTime;

        Calendar calendarForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendarForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarForDate.getTime());

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    link = snapshot.child("image").getValue(String.class);
                    Toast.makeText(ProductDetailsActivity.this, "ImageURL:"+link, Toast.LENGTH_SHORT).show();

                final  DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");

                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("pid",productID);
                cartMap.put("name",productName.getText().toString());
                cartMap.put("price",productPrice.getText().toString());
                cartMap.put("description",productDescription.getText().toString());
                cartMap.put("period",productPeriod.getText().toString());
                cartMap.put("image", link);

                cartListRef.child(currentUser).child("Products in Cart").child(productID)
                        .updateChildren(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(ProductDetailsActivity.this, "Product Added To Cart", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                                    intent.putExtra("pid",productID);
                                    startActivity(intent);
                                }
                            }
                        });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




    }

    private void getProductDetails(String productID) {

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    Products products = snapshot.getValue(Products.class);

                    productName.setText(products.getName());
                    productPrice.append(""+products.getRate());
                    productPeriod.append(" "+products.getPeriod());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}