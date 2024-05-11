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

public class SportsAdapter  extends RecyclerView.Adapter<SportsAdapter.MyViewHolder> {
    private Context context;
    private  List<SportModel> sportModelList;
    private String userId;
    public SportsAdapter(Context context) {
        this.context = context;
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.sportModelList = new ArrayList<>();
    }

    public  void add(SportModel sportModel)
    {
        sportModelList.add(sportModel);
    }

    public void clear(){
        sportModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SportModel sportModel=sportModelList.get(position);
        holder.sportName.setText(sportModel.getSportName());
        holder.sportCalori.setText(sportModel.getSportCalori());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCaloriesToDatabase(userId, sportModel.getSportCalori());
                Log.d("SportsAdapter", "Calories added to database"); // Log mesajı ekle
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
                    myRef.child(userId).child(date).child("dropCalori").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String dropCaloriString = dataSnapshot.getValue(String.class);
                                Long currentCalories = Long.parseLong(dropCaloriString);
                                Long newCalories = currentCalories + calori; // Yeni değeri hesapla
                                Log.d("SportsAdapter", "kontrol:7");

                                myRef.child(userId).child(date).child("dropCalori").setValue(String.valueOf(newCalories));
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
                                        Log.d("SportsAdapter", "changedCalori database error");
                                        Toast.makeText(context, "changedCalori database error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Log.d("SportsAdapter", "kontrol:9");
                                myRef.child(userId).child(date).child("dropCalori").setValue(String.valueOf(calori));
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("SportsAdapter", "datbase eror"); // Log mesajı ekle
                            Toast.makeText(context, "database eror", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d("SportsAdapter", "kontrol:10");
                    myRef.child(userId).child(date).child("dropCalori").setValue(String.valueOf(calori));
                    myRef.child(userId).child(date).child("date").setValue(date);
                    myRef.child(userId).child(date).child("takeCalori").setValue("0");
                    myRef.child(userId).child(date).child("changedCalori").setValue(String.valueOf(calori));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("SportsAdapter", "datbase eror2"); // Log mesajı ekle
                Toast.makeText(context, "database eror2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sportModelList.size();
    }
    public List<SportModel> getSportModelList(){
        return sportModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView sportName, sportCalori;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sportName = itemView.findViewById(R.id.sporname);
            sportCalori = itemView.findViewById(R.id.sporcalori);
        }
    }
}

