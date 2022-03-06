package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ItemViewHolder> {


    private ArrayList<String> arrayList;
    private Context mContext;
    private ArrayList<String> subArrayList;

    TextView tv_title;
    ImageView img;

    ExerciseData data;

    private ArrayList<ExerciseData> listData = new ArrayList<>();


    //public ExerciseAdatper(ArrayList<String> arrayList, ArrayList<String> subArrayList, Context mContext){
    public ExerciseAdapter(){

    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img_exercise;

        protected RecyclerView recyclerView;

        ItemViewHolder(View itemView){
            super(itemView);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.rv_exercise);

            title = itemView.findViewById(R.id.tv_exercise);
            img_exercise = itemView.findViewById(R.id.iv_exercise);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(v.getContext(), GuideActivity.class);
                        v.getContext().startActivity(intent);

                    }

                }
            });

        }

        public void onBind(ExerciseData data){
            title.setText(data.getTitle());
            //iv_add_menu.setImageResource(data.getImage());
            String imageUrl = data.getImage();
            Glide.with(this.itemView.getContext()).load(imageUrl).into(img_exercise);

        }
    }

    @NonNull
    @Override
    public ExerciseAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exercise, parent, false);
        mContext = parent.getContext();
        return new ExerciseAdapter.ItemViewHolder(view);

       // return new ExerciseAdapter().ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                ExerciseData item = listData.get(position);
                Intent intent = new Intent(mContext, GuideActivity.class);
                intent.putExtra("id", String.valueOf(item.getId()));
                mContext.startActivity(intent);
            }
        });

    }

    public int getItemCount(){
        return listData.size();
    }
    public void addItem(ExerciseData data){
        listData.add(data);
    }
}
