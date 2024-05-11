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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class SportActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SportsAdapter sportsAdapter;
    private String currentUserUid;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        getSupportActionBar().setTitle(userName);

        sportsAdapter = new SportsAdapter(this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(sportsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("sports");
        currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadUserRecords();
    }
    private void loadUserRecords() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sportsAdapter.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    SportModel sportModel = dataSnapshot.getValue(SportModel.class);
                    if(sportModel!=null && sportModel.getSportName()!=null){
                        sportsAdapter.add(sportModel);
                    }
                }
                List<SportModel> sportModelList=sportsAdapter.getSportModelList();
                sportsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SportActivity", "kontrol:5");
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SportActivity.this, MainActivity.class);
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
            startActivity(new Intent(SportActivity.this, SigninActivity.class));
            finish();
            return true;
        }
        return false;
    }
}