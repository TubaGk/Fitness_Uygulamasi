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


public class RecordActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecordsAdapter recordsAdapter;
    private DatabaseReference recordReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String userName=getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(userName);

        recordsAdapter = new RecordsAdapter(this);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setAdapter(recordsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recordReference = FirebaseDatabase.getInstance().getReference("records/"+userId);

        loadUserRecords();
    }
    private void loadUserRecords() {
        recordReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recordsAdapter.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    try {
                        RecordModel recordModel = dataSnapshot.getValue(RecordModel.class);
                        if(recordModel != null){
                            recordsAdapter.add(recordModel);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(recordsAdapter.getItemCount() > 0) {
                    recordsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("RecordActivity", "kontrol:4");
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RecordActivity.this, MainActivity.class);
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
            startActivity(new Intent(RecordActivity.this, SigninActivity.class));
            finish();
            return true;
        }
        return false;
    }
}