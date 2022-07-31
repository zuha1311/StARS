package com.example.producta.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.producta.CartViewHolder;
import com.example.producta.Controller.Cart;
import com.example.producta.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button proceedToCheckout;
    private String totalAmountWithGBP, finalTotalAmount;
    private FirebaseAuth mAuth;
    private String currentUser;
    private DatabaseReference rootRef;
    private String imageLink;
    private String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecycledView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();

        productID = getIntent().getStringExtra("pid");
        Toast.makeText(this, "Product ID: "+productID, Toast.LENGTH_SHORT).show();

        rootRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(currentUser).child("Products in Cart").child(productID);
       rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               if(snapshot.exists())
               {
                   totalAmountWithGBP = snapshot.child("price").getValue(String.class);
                   finalTotalAmount = totalAmountWithGBP.substring(1);
                   Toast.makeText(CartActivity.this, "Total Amount: "+finalTotalAmount, Toast.LENGTH_SHORT).show();


               }
           }

           @Override
           public void onCancelled(@NonNull @NotNull DatabaseError error) {

           }
       });
        //Toast.makeText(this, "Total Amount:"+totalAmount, Toast.LENGTH_SHORT).show();

        proceedToCheckout = findViewById(R.id.proceedToCheckoutButton);

        proceedToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("rate", finalTotalAmount);
                intent.putExtra("pid",productID);
                startActivity(intent);
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:

                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(currentUser).child("Products in Cart");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef, Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull CartViewHolder holder, int position, @NonNull @NotNull Cart model) {
                            holder.productName.setText(model.getName());
                            holder.productPrice.append(""+model.getPrice() +" "+ model.getPeriod());
                            Picasso.get().load(model.getImage()).into(holder.productImage);
                            //Picasso.get().setLoggingEnabled(true);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Remove",

                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if(i==0)
                                        {
                                            cartListRef.child(model.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                                    if(task.isSuccessful())
                                                                    {
                                                                        Toast.makeText(CartActivity.this, "Item removed from cart", Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent(CartActivity.this, SearchActivity.class);
                                                                        startActivity(intent);

                                                                    }
                                                        }
                                                    });
                                        }

                                    }
                                });

                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @NotNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}