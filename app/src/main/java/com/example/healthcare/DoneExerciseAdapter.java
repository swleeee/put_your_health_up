package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

class DoneExerciseAdapter extends RecyclerView.Adapter<DoneExerciseAdapter.ItemViewHolder> {

    TextView tv_title;
    TextView tv_count;
    TextView tv_date;

    DoneExerciseData data;

    private ArrayList<DoneExerciseData> listData = new ArrayList<>();


    public DoneExerciseAdapter(){

    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView count;
        private TextView date;

        protected RecyclerView recyclerView;

        ItemViewHolder(View itemView){
            super(itemView);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.rv_done_exercise);

            title = itemView.findViewById(R.id.tv_done_exercise_name);
            count = itemView.findViewById(R.id.tv_done_exercise_count);
            date = itemView.findViewById(R.id.tv_done_exercise_date);

        }

        public void onBind(DoneExerciseData data){
            title.setText(data.getTitle());
            count.setText(data.getCount());
            date.setText(data.getDate());

        }
    }

    @NonNull
    @Override
    public DoneExerciseAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_done_exercise, parent, false);
        //mContext = parent.getContext();
        //return new ItemViewHolder(view);

        return new DoneExerciseAdapter.ItemViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull DoneExerciseAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));

    }

    public int getItemCount(){
        return listData.size();
    }
    public void addItem(DoneExerciseData data){
        listData.add(data);
    }
}
