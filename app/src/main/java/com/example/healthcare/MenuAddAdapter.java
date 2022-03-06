package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAddAdapter extends RecyclerView.Adapter<MenuAddAdapter.ViewHolder> {
    private ArrayList<MenuAddData> mData = null ;
    Context context; int count = 0;
    MenuAddAdapter(ArrayList<MenuAddData> list) {
        mData = list;
    }
    @NonNull
    @Override
    public MenuAddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.list_add_menu, viewGroup, false) ;
        MenuAddAdapter.ViewHolder vh = new MenuAddAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAddAdapter.ViewHolder viewHolder, int position) {
        MenuAddData item = mData.get(position);
        count++;
        viewHolder.txt_Foodname.setText(item.getFoodname());
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String position2 = (String) viewHolder.spinner.getSelectedItem();
                Intent intent = new Intent("custom-message");
                intent.putExtra("position",position2);
                intent.putExtra("i",position);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                Log.i("position intent=", "값은" + position2 + " " + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Foodname; Spinner spinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_Foodname=itemView.findViewById(R.id.txt_Foodname);
            spinner = itemView.findViewById(R.id.spinner);
        }
    }
}
