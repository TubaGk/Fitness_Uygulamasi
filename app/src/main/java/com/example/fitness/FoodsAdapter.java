package com.example.fitness;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.MyViewHolder> {
    private Context context;
    private List<FoodModel> foodModelList;
    private String userId;

    public FoodsAdapter(Context context) {
        this.context = context;
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.foodModelList = new ArrayList<>();
    }

    public void add(FoodModel foodModel) {
        foodModelList.add(foodModel);
    }

    public void clear() {
        foodModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodsAdapter.MyViewHolder holder, int position) {
        FoodModel foodModel = foodModelList.get(position);
        holder.foodName.setText(foodModel.getFoodName());
        holder.foodCalori.setText(foodModel.getFoodCalori());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCaloriesToDatabase(userId, foodModel.getFoodCalori());
                Log.d("FoodsAdapter", "Calories added to database"); // Log mesajı ekle
                Toast.makeText(context, "Calories added to database", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addCaloriesToDatabase(String userId, String calories) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("records");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date = formatter.format(calendar.getTime());
        final int calori = Integer.parseInt(calories);

        myRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(date)) {
                    myRef.child(userId).child(date).child("takeCalori").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String takeCaloriString = dataSnapshot.getValue(String.class);
                                Long currentCalories = Long.parseLong(takeCaloriString);
                                Long newCalories = currentCalories + calori;
                                Log.d("FoodsAdapter", "kontrol:0");

                                myRef.child(userId).child(date).child("takeCalori").setValue(String.valueOf(newCalories));
                                myRef.child(userId).child(date).child("changedCalori").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String changedCaloriString = snapshot.getValue(String.class);
                                            int currentChangedCalories = Integer.parseInt(changedCaloriString);
                                            int newChangedCalories = currentChangedCalories + calori;
                                            myRef.child(userId).child(date).child("changedCalori").setValue(String.valueOf(newChangedCalories));
                                        } else {
                                            myRef.child(userId).child(date).child("changedCalori").setValue(String.valueOf(calori));
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.d("FoodsAdapter", "changedCalori database error");
                                        Toast.makeText(context, "changedCalori database error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Log.d("FoodsAdapter", "kontrol:1");
                                myRef.child(userId).child(date).child("takeCalori").setValue(String.valueOf(calori));
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("FoodsAdapter", "datbase eror");
                            Toast.makeText(context, "database eror", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d("FoodsAdapter", "kontrol:2");
                    myRef.child(userId).child(date).child("takeCalori").setValue(String.valueOf(calori));
                    myRef.child(userId).child(date).child("date").setValue(date);
                    myRef.child(userId).child(date).child("dropCalori").setValue("0");
                    myRef.child(userId).child(date).child("changedCalori").setValue(String.valueOf(calori));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FoodsAdapter", "datbase eror2"); // Log mesajı ekle
                Toast.makeText(context, "database eror2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public List<FoodModel> getFoodModelList() {
        return foodModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView foodName, foodCalori;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodname);
            foodCalori = itemView.findViewById(R.id.foodcalori);
        }
    }
}

