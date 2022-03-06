package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ItemViewHolder> {

    TextView menu_title;
    TextView menu_calory;

    MenuData data;

    //private ArrayList<MyItem> listData = new ArrayList<>();
    private ArrayList<MenuData> listData = new ArrayList<>();


    @NonNull
    @Override
    public MenuAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);

        return new ItemViewHolder(view);


    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView menuName;
        private TextView menuCalory;
        private ImageButton deleteMenu;

        ItemViewHolder(View itemView){
            super(itemView);

            menuName = itemView.findViewById(R.id.menu_name);
            menuCalory = itemView.findViewById(R.id.menu_calory);
            // deleteMenu = itemView.findViewById(R.id.btn_delete_menu);

            //itemView.findViewById(R.id.btn_delete_menu);
//            deleteMenu.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    int pos = getAdapterPosition();
////                    if(pos != RecyclerView.NO_POSITION){
////                        //Intent intent = new Intent(v.getContext(), GetImageActivity.class);
////                        //Toast.makeText(v.getContext(), String.valueOf(pos), Toast.LENGTH_LONG).show();
//////                        Intent intent = new Intent(v.getContext(), CameraActivity.class);
//////                        v.getContext().startActivity(intent);
////
////                    }
//
////                }
//            });
        }

        public void onBind(MenuData data){

            //tv_day_title.setText(data.getTitle());
            menuName.setText(data.getTitle());
            menuCalory.setText(data.getCalory());
            //iv_add_menu.setImageResource(data.getImage());
            //deleteMenu.setImageResource(data.getImage());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }


    @Override
    public int getItemCount(){
        return listData.size();
    }
    public void addItem(MenuData data){
        listData.add(data);
    }

}

