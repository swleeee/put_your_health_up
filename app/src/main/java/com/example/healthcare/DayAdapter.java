package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ItemViewHolder> {

    private ArrayList<String> arrayList;
    private Context mContext;
    private ArrayList<String> subArrayList;

    TextView tv_day_title;
    ImageButton iv_add_menu;

    DayData data;

    //private ArrayList<MyItem> listData = new ArrayList<>();
    private ArrayList<DayData> listData = new ArrayList<>();


    //public DayAdapter(ArrayList<String> arrayList, ArrayList<String> subArrayList, Context mContext){
    public DayAdapter(Context mContext){
//        this.arrayList = arrayList;
        this.mContext = mContext;
//        this.subArrayList = subArrayList;
//        super(itemView);
//
//        tv_day_title = itemView.findViewById(R.id.tv_title);
//        iv_add_menu = itemView.findViewById((R.id.btn_add_menu));
//
//
  }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView dayName;
        private ImageButton addMenu;
        protected RecyclerView recyclerView;



        ItemViewHolder(View itemView){
            super(itemView);
            this.recyclerView = (RecyclerView)itemView.findViewById(R.id.menu_rv);

            dayName = itemView.findViewById(R.id.tv_title);
            addMenu = itemView.findViewById(R.id.btn_add_menu);

            itemView.findViewById(R.id.btn_add_menu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(v.getContext(), GetImageActivity.class);
                        Toast.makeText(v.getContext(), String.valueOf(pos), Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(v.getContext(), CameraActivity.class);
                        v.getContext().startActivity(intent);

                    }

                }
            });
        }

        public void onBind(DayData data){


            //RecyclerView s = (RecyclerView)itemView.findViewById(R.id.menu_rv);
            RecyclerView s = recyclerView;
            MenuAdapter menuAdapter = new MenuAdapter();
            s.setAdapter(menuAdapter);
            LinearLayoutManager layoutmanager = new LinearLayoutManager(mContext);
            s.setLayoutManager(layoutmanager);
//            s.setHasFixedSize(true);

//            R.layout.fragment_menu_analysis,
//            recyclerView = (RecyclerView) rootView.findViewById(R.id.day_rv);
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//            recyclerView.setLayoutManager(linearLayoutManager);

             //tv_day_title.setText(data.getTitle());
            dayName.setText(data.getTitle());
             //iv_add_menu.setImageResource(data.getImage());
            addMenu.setImageResource(data.getImage());
        }
    }

    @NonNull
    @Override
    public DayAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        Context context = parent.getContext();
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_day, parent, false);

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

        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));

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
    public void addItem(DayData data){
        listData.add(data);
    }
}
