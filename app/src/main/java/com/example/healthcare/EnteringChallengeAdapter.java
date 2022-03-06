package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EnteringChallengeAdapter extends RecyclerView.Adapter<EnteringChallengeAdapter.ItemViewHolder> {

    private ArrayList<String> arrayList;
    private Context mContext;
    private ArrayList<String> subArrayList;
    private ImageView img_challenge;
    TextView tv_title;
    ImageView img;

    EnteringChallengeData data;

    private ArrayList<EnteringChallengeData> listData = new ArrayList<>();


    //public EnteringChallengeAdapter(ArrayList<String> arrayList, ArrayList<String> subArrayList, Context mContext){
    public EnteringChallengeAdapter(){
//        this.arrayList = arrayList;

//        this.subArrayList = subArrayList;
//        super(itemView);
//
//        tv_title = itemView.findViewById(R.id.tv_title);
//        iv_add_menu = itemView.findViewById((R.id.btn_add_menu));
//
//
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img_menu;
        private CardView cardView;
        protected RecyclerView recyclerView;


        ItemViewHolder(View itemView){
            super(itemView);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.menu_rv);

            title = itemView.findViewById(R.id.tv_entering_challenge_name);
            img_menu = itemView.findViewById(R.id.img_entering_challenge);
            img_challenge = itemView.findViewById(R.id.img_entering_challenge);
            cardView = itemView.findViewById(R.id.lt_cardview);

//            TextView itemTextView = (TextView)itemView;


            //(TextView) holder.itemView.findViewById(R.id.tv_)
            //itemTextView.setText(data.get(position).text);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //   name = v.findViewById(R.id.header_title);
                    Intent intent = new Intent(v.getContext(), PopUpAuthentication.class);
                    // Toast.makeText(v.getContext(), name.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(v.getContext(), String.valueOf(pos), Toast.LENGTH_LONG).show();
////                        Intent intent = new Intent(v.getContext(), CameraActivity.class);

                    //intent.putExtra("menu", itemTextView.getText());

                    v.getContext().startActivity(intent);
                }
            });
        }

        public void onBind(EnteringChallengeData data){

            title.setText(data.getTitle());
            //iv_add_menu.setImageResource(data.getImage());
            String imageUrl = data.getImage();
            Glide.with(this.itemView.getContext()).load(imageUrl).into(img_challenge);
        }
    }

    @NonNull
    @Override
    public EnteringChallengeAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        Context context = parent.getContext();
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_entering_challenge, parent, false);

//        view.findViewById(R.id.btn_add_menu);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //int pos = RecyclerView.getC
//                //if(pos != RecyclerView.NO_POSITION){
//                    //Intent intent = new Intent(v.getContext(), GetImageActivity.class);
//                    Intent intent = new Intent(v.getContext(), CameraActivity.class);
//                    v.getContext().startActivity(intent);
//
//                //}
//
//            }
//        });

        return new EnteringChallengeAdapter.ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EnteringChallengeAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
        EnteringChallengeData item = listData.get(position);
        if(item.getIs_success() == 1){
            holder.cardView.setCardBackgroundColor(Color.rgb(241, 242, 241));
            holder.title.setText(item.getTitle() + " (완료)");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnteringChallengeData item = listData.get(position);
                if(item.getIs_success() == 0){
                    Intent intent = new Intent(mContext, PopUpAuthentication.class);
                    intent.putExtra("kinds", item.getKinds());
                    intent.putExtra("id", String.valueOf(item.getId()));
                    mContext.startActivity(intent);
                }
                else{
                    Toast.makeText(mContext, "인증 성공이 완료된 챌린지입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //RecyclerView s = itemView.findViewById(R.id.menu_rv);
//            holder.
////    setHasFixedSize(true);
//            MenuAdapter menuAdapter = new MenuAdapter();
////            s.setAdapter(menuAdapter);
//            LinearLayoutManager layoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
////            s.setLayoutManager(layoutmanager);

    }


    public int getItemCount(){
        return listData.size();
    }
    public void addItem(EnteringChallengeData data){
        listData.add(data);
    }
}