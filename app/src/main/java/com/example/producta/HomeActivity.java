package com.example.producta;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private ImageView golf, tennis, tableTennis, football;
    private LinearLayout outdoorSportLayout;
    private TextView username;
    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private ImageView explore, renter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

       golf = findViewById(R.id.golfButton);
       tennis = findViewById(R.id.tennisButton);
       tableTennis = findViewById(R.id.tableTennisButton);
       football = findViewById(R.id.footballButton);

       username = findViewById(R.id.usernameText);

       explore = findViewById(R.id.exploreButton);
       renter = findViewById(R.id.renterSignUpButton);

       outdoorSportLayout = findViewById(R.id.outdoorSportLayout);
       outdoorSportLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

       mAuth = FirebaseAuth.getInstance();
       firebaseUser = mAuth.getCurrentUser();

       explore.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
               startActivity(intent);
           }
       });

       renter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(HomeActivity.this,Renter.class);
               startActivity(intent);
           }
       });

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:

                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void expand(View view) {



        int g = (golf.getVisibility()== View.GONE)? View.VISIBLE: View.GONE;
        int t = (tennis.getVisibility()== View.GONE)? View.VISIBLE: View.GONE;
        int tt = (tableTennis.getVisibility()== View.GONE)? View.VISIBLE: View.GONE;
        int f = (football.getVisibility()== View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(outdoorSportLayout, new AutoTransition());
        golf.setVisibility(g);
        tennis.setVisibility(t);
        tableTennis.setVisibility(tt);
        football.setVisibility(f);
    }
}