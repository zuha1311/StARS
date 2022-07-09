package com.example.producta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpButton;
    private EditText username,password, email;
    private FirebaseDatabase database;
    private DatabaseReference UserReference;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private static final String USER = "user";
    private static final String TAG = "RegisterActivity";
    private Users user;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        UserReference = database.getReference(USER);

        mAuth = FirebaseAuth.getInstance();


        signUpButton = findViewById(R.id.signUpButton);
        username = findViewById(R.id.userNameEditText);
        password = findViewById(R.id.passwordEditText);
        email = findViewById(R.id.emailEditText);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUpActivity.this, "Please enter all the details to sign up", Toast.LENGTH_SHORT).show();
                }

                else{
                    final String emailforDB = email.getText().toString();
                    final String passwordforDB = password.getText().toString();
                    final String usernameforDB = username.getText().toString();

                    user = new Users(usernameforDB,passwordforDB,emailforDB);

                    saveProfileInfo(emailforDB,passwordforDB);
                }
            }
        });



    }

    private void saveProfileInfo(String emailforDB, String passwordforDB) {

//        final DatabaseReference RootRef;
//        RootRef = FirebaseDatabase.getInstance().getReference();
//        Toast.makeText(this, "Executing save profile info currentUserId"+currentUserId, Toast.LENGTH_SHORT).show();
//
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if(!(snapshot.child("Users").child(currentUserId).exists()))
//                {
//                    HashMap<String,Object> userDataMap = new HashMap<>();
//                    userDataMap.put("uid",currentUserId);
//                    userDataMap.put("username",usernameforDB);
//                    userDataMap.put("password",passwordforDB);
//
//                    RootRef.child("Users").child(currentUserId).updateChildren(userDataMap)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful())
//                                    {
//
//                                        Toast.makeText(SignUpActivity.this, "Cogrtulations, your account has been created", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                    }
//                                }
//                            });
//
//                }
//
//                else
//                {
//                    Toast.makeText(SignUpActivity.this, "Failure to create account", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        mAuth.createUserWithEmailAndPassword(emailforDB,passwordforDB)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "Congratulations! Registration Successful", Toast.LENGTH_SHORT).show();


                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                        else
                        {
                            Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }
                    }
                });

    }

    private void updateUI(FirebaseUser currentUser) {
        String keyId = UserReference.push().getKey();
        UserReference.child(keyId).setValue(user);


    }
}