package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;

public class FoodActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FoodsAdapter foodsAdapter;
    private DatabaseReference recordReference;
    private String userId;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        getSupportActionBar().setTitle(userName);

        foodsAdapter = new FoodsAdapter(this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(foodsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("foods");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadUserRecords();

    }
        private void loadUserRecords() {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    foodsAdapter.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FoodModel foodModel = dataSnapshot.getValue(FoodModel.class);
                        if (foodModel != null && foodModel.getFoodName() != null ) {
                            foodsAdapter.add(foodModel);
                        }
                    }
                    List<FoodModel> foodModelList = foodsAdapter.getFoodModelList();
                    foodsAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("FoodActivity", "kontrol:6");
                }
            });
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FoodActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(FoodActivity.this, SigninActivity.class));
            finish();
            return true;
        }
        return false;
    }
}