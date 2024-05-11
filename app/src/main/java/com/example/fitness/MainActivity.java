package com.example.fitness;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    ImageButton foodbtn,sportbtn,recordbtn;
    Button hesaplabtn;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodbtn=findViewById(R.id.foodImgbtn);
        sportbtn=findViewById(R.id.sportImgbtn);
        recordbtn=findViewById(R.id.recordImgbtn);
        hesaplabtn=findViewById(R.id.hspl);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        getSupportActionBar().setTitle(userName);

        hesaplabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, UpdateActivity.class);
                startActivity(intent);
            }
        });

        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                intent.putExtra("name", username);
                startActivity(intent);
                finish();
            }
        });

        sportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                Intent intent = new Intent(MainActivity.this, SportActivity.class);
                intent.putExtra("name", username);
                startActivity(intent);
                finish();
            }
        });

        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "buton çalıştı", Toast.LENGTH_SHORT).show();
                String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                intent.putExtra("name", username);
                startActivity(intent);
                finish();
            }
        });
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
            startActivity(new Intent(MainActivity.this, SigninActivity.class));
            finish();
            return true;
        }
        return false;
    }
}