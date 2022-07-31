package com.example.producta.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.producta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private TextView totalPrice;
    private String price, durationMultiplier, currentUser, productID;
    private Button confirmOrderButton;
    private EditText name, address, mobile, duration;
    private int priceInt;
    private DatabaseReference UserReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getUid();

        totalPrice = findViewById(R.id.PriceText);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);
        name = findViewById(R.id.customerNameEditText);
        address = findViewById(R.id.customerAddressEditText);
        mobile = findViewById(R.id.customerMobileEditText);
        duration = findViewById(R.id.customerTimeEditText);

        price = getIntent().getStringExtra("rate");
        totalPrice.setText(price);

        productID = getIntent().getStringExtra("pid");

        duration.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(!duration.getText().toString().isEmpty())
               {
                   durationMultiplier = duration.getText().toString();

                   priceInt = Integer.parseInt(price) * Integer.parseInt(durationMultiplier);
                    price = String.valueOf(priceInt);
                   totalPrice.setText(price);
               }
               else
               {
                   totalPrice.setText(price);
               }

           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty() || address.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() || duration.getText().toString().isEmpty()){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please enter all the details to proceed with the order", Toast.LENGTH_LONG).show();
                }

                else{
                    final String nameForDB = name.getText().toString();
                    final String addressForDB = address.getText().toString();
                    final String mobileForDB = mobile.getText().toString();
                    final String durationForDB = duration.getText().toString();

                    addOrder(nameForDB,addressForDB,mobileForDB,durationForDB,price);



                }
            }
        });

    }

    private void addOrder(String nameForDB, String addressForDB, String mobileForDB, String durationForDB, String totalPrice) {

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("name", nameForDB);
        orderMap.put("address",addressForDB);
        orderMap.put("duration",durationForDB);
        orderMap.put("productID",productID);
        orderMap.put("totalPrice",totalPrice);

        rootRef.child("Orders").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.exists())
                {
                    rootRef.child("Orders").child(currentUser).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(ConfirmFinalOrderActivity.this, "Order added to database", Toast.LENGTH_SHORT).show();
                            rootRef.child("Products").child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Product removed from Products", Toast.LENGTH_SHORT).show();
                                        rootRef.child("Cart").child(currentUser).child("Products in Cart").child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {

                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(ConfirmFinalOrderActivity.this, "Product removed from cart ", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);

                                                }

                                            }
                                        });

                                    }
                                }
                            });
                        }
                    }
                });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });






    }
}