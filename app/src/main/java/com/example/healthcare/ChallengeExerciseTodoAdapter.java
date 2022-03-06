package com.example.healthcare;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChallengeExerciseTodoAdapter extends RecyclerView.Adapter<ChallengeExerciseTodoAdapter.ItemViewHolder> {

    TextView tv_title;
    ImageView img;

    String data;
    Context mContext;
    private ArrayList<ChallengeExerciseTodoData> listData = new ArrayList<>();
    String [] ex_count = new String[2]; // 운동 종류에 맞게 숫자 수정

    public String[] getEx_count() {
        return ex_count;
    }

    public void setEx_count(String[] ex_count) {
        this.ex_count = ex_count;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private EditText count;
        private ImageButton delete_item;
        protected RecyclerView recyclerView;

        ItemViewHolder(View itemView){
            super(itemView);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.rv_challenge_exercise_todo);

            title = itemView.findViewById(R.id.tv_challenge_exercise_title);
            count = itemView.findViewById(R.id.tv_challenge_exercise_count);
            delete_item = itemView.findViewById(R.id.btn_challenge_exercise_delete);

            delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getAdapterPosition());
                    Log.d("fff", String.valueOf(((AddChallenge)mContext).spinner_count));
                    ((AddChallenge)mContext).spinner_count -= 1;
                }
            });

        }

        public void onBind(ChallengeExerciseTodoData data){
            title.setText(data.getTitle());
            count.setText(data.getCount());
        }
    }

    public ChallengeExerciseTodoAdapter(){

    }

    public ChallengeExerciseTodoAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public ChallengeExerciseTodoAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_challenge_exercise_todo, parent, false);
        return new ChallengeExerciseTodoAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeExerciseTodoAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
        holder.count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ex_count[position] = s.toString();
                Log.d("aa", Arrays.toString(getEx_count()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void removeItem(int position){
        listData.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount(){
        return listData.size();
    }
    public void addItem(ChallengeExerciseTodoData data){
        listData.add(data);
    }

    public int getPosition(){
        return getPosition();
    }
}
