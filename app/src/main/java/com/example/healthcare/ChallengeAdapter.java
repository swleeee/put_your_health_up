package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ItemViewHolder> {

    private ArrayList<String> arrayList;
    private Context mContext;
    private ArrayList<String> subArrayList;

    TextView tv_title;
    ImageView img;

    ChallengeData data;

    private ArrayList<ChallengeData> listData = new ArrayList<>();


    //public ChallengeAdapter(ArrayList<String> arrayList, ArrayList<String> subArrayList, Context mContext){
    public ChallengeAdapter(){
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
        private TextView recruit_deadline;
        private TextView challenge_period;

        private ImageView img_challenge;

        protected RecyclerView recyclerView;
        private Button challenge_detail;

        ItemViewHolder(View itemView){
            super(itemView);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.rv_challenge);

            title = itemView.findViewById(R.id.tv_challenge_name);
            recruit_deadline = itemView.findViewById(R.id.tv_recruit_deadline);
            challenge_period = itemView.findViewById(R.id.tv_challenge_period);

            img_challenge = itemView.findViewById(R.id.img_challenge);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(v.getContext(), ChallengeDetail.class);
                        v.getContext().startActivity(intent);
                    }
                }
            });

//            challenge_detail = itemView.findViewById(R.id.btn_add_challenge);
//
//            //itemView.findViewById(R.id.btn_add_challenge);
//            challenge_detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if(pos != RecyclerView.NO_POSITION){
//                        Intent intent = new Intent(v.getContext(), ChallengeDetail.class);
//                        v.getContext().startActivity(intent);
//                    }
//                }
//            });
        }

        public void onBind(ChallengeData data){
            title.setText(data.getTitle());
            //iv_add_menu.setImageResource(data.getImage());
            String imageUrl = data.getImage();
            Glide.with(this.itemView.getContext()).load(imageUrl).into(img_challenge);

            recruit_deadline.setText(data.getRecruit_deadline());
            challenge_period.setText(data.getChallenge_period());
        }
    }

    @NonNull
    @Override
    public ChallengeAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        Context context = parent.getContext();
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_challenge, parent, false);
        mContext = parent.getContext();

//        view.findViewById(R.id.btn_add_menu);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //int pos = RecyclerView.getC
//                //if(pos != RecyclerView.NO_POSITION){
//                    //Intent intent = new Intent(v.getContext(), GetImageActivity.class);
//                    Intent intent = new Intent(v.getContext(), CameraActivity.class);
//                    v.getContext().startActivity(intent);
//                //}
//            }
//        });
        return new ChallengeAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                ChallengeData item = listData.get(position);
                Intent intent = new Intent(mContext, ChallengeDetail.class);
                intent.putExtra("id", String.valueOf(item.getId()));
                intent.putExtra("member_id", String.valueOf(item.getMember_id()));
                mContext.startActivity(intent);
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
    public void addItem(ChallengeData data){
        listData.add(data);
    }
}
