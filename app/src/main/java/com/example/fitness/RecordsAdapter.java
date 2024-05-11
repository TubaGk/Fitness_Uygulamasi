package com.example.fitness;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecordsAdapter  extends RecyclerView.Adapter<RecordsAdapter.MyViewHolder> {
    private Context context;
    private  List<RecordModel> recordModelList;
    public RecordsAdapter(Context context) {
        this.context = context;
        this.recordModelList = new ArrayList<>();
    }

    public  void add(RecordModel recordModel)
    {
        recordModelList.add(recordModel);
    }

    public void clear(){
        recordModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull RecordsAdapter.MyViewHolder holder, int position) {
        RecordModel recordModel=recordModelList.get(position);
        holder.date.setText(recordModel.getDate());
        holder.dropCalori.setText(recordModel.getDropCalori());
        holder.takeCalori.setText(recordModel.getTakeCalori());
        holder.changeCalori.setText(recordModel.getChangedCalori());
    }

    @Override
    public int getItemCount() {
        return recordModelList.size();
    }
    public List<RecordModel> getRecordModelList(){
        return recordModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView date, takeCalori,dropCalori,changeCalori;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tarih);
            takeCalori = itemView.findViewById(R.id.foodcalori);
            dropCalori = itemView.findViewById(R.id.sporcalori);
            changeCalori = itemView.findViewById(R.id.changecalori);
        }
    }
}
