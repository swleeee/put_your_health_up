package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> data = new ArrayList<>();

    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int prePosition = -1;

    public ExpandableListAdapter(List<Item> data) {
        this.data = data;
    }

    public ExpandableListAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        Log.i("onCreateViewHolder", "onCreateViewHolder");
        View view = null;
        final Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                Log.i("헤더인플레이터", "잘된다");
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                Log.i("차일드인플레이터", "잘된다");
                LayoutInflater inflater2 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater2.inflate(R.layout.list_child, parent, false);
                ListChildViewHolder header2 = new ListChildViewHolder(view);
                return header2;
//                TextView itemTextView = new TextView(context);
//                TextView itemTextView2 = new TextView(context);
//                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
//                itemTextView.setTextColor(0x88000000);
//                itemTextView.setLayoutParams(
//                        new ViewGroup.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.WRAP_CONTENT));
//                return new RecyclerView.ViewHolder(itemTextView) {
//                };
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("onBindViewHolder", "onBindViewHolder");

        //TextView name;
        //data = new ArrayList<>();

        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
//                SharedPreferences sharedPreferences = context.getSharedPreferences("position", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("position", String.valueOf(position));
//                editor.commit();
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;

                Log.i("헤더", "잘된다");
                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);
                itemController.header_calory.setText(item.calory);
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.arrow_up);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.arrow_down);
                }


                itemController.add_temp.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), GetImageActivity.class);
                        intent.putExtra("position" , position);
//                Toast.makeText(v.getContext(), String.valueOf(pos), Toast.LENGTH_LONG).show();
////                        Intent intent = new Intent(v.getContext(), CameraActivity.class);
                        v.getContext().startActivity(intent);
                    }
                });

                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("토글 버튼", "누름1");
                        if (item.invisibleChildren == null) {
//                            if(selectedItems.get(position)){
//                                selectedItems.delete(position);
//                            }else{
//                                selectedItems.delete(prePosition);
//                                selectedItems.put(position, true);
//
//                            }
//                            if(prePosition != -1)
//                                notifyItemChanged(prePosition);
//                            notifyItemChanged(position);
//                            prePosition = position;


                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.arrow_down);
                            Log.i("토글 버튼", "누름2");
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.arrow_up);
                            item.invisibleChildren = null;
                            Log.i("토글 버튼", "누름3");
                        }
                    }
                });
                break;
            case CHILD:
                Log.i("차일드", "잘된다");
                final ListChildViewHolder itemController2 = (ListChildViewHolder) holder;
                itemController2.child_title.setText(item.text);
                itemController2.child_calory.setText(item.calory);
                Log.i("어뎁터의 text, calory값", item.text + item.calory);
//                TextView itemTextView = (TextView) holder.itemView;
//                TextView itemTextView2 = (TextView) holder.itemView;
//                Log.i("어뎁터의 text, calory값", item.text + item.calory + " " + itemTextView);
////
////
////                //(TextView) holder.itemView.findViewById(R.id.tv_)
//                itemTextView.setText(data.get(position).text);
//                itemTextView2.setText(data.get(position).calory);
//                itemTextView.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(v.getContext(), MenuDetailActivity.class);
//                        v.getContext().startActivity(intent);
//                    }
//                });
//                itemTextView2.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(v.getContext(), MenuDetailActivity.class);
//                        v.getContext().startActivity(intent);
//                    }
//                });
//                itemController2.child_calory.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(v.getContext(), MenuDetailActivity.class);
//                        v.getContext().startActivity(intent);
//                    }
//                });



                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(MenuAnalysisData data) {
    }

    public void addItem(Item data) {
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public TextView header_calory;
        public Item refferalItem;
        public ImageButton add_temp;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
            header_calory = (TextView) itemView.findViewById(R.id.header_calory);
            add_temp = (ImageButton) itemView.findViewById(R.id.btn_add_temp);
        }
    }
    private static class ListChildViewHolder extends RecyclerView.ViewHolder{
        TextView child_title, child_calory;

        public ListChildViewHolder(@NonNull View itemView) {
            super(itemView);
            child_title = itemView.findViewById(R.id.child_title);
            child_calory = itemView.findViewById(R.id.child_calory);

        }
    }

    public static class Item {
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCalory() {
            return calory;
        }

        public void setCalory(String calory) {
            this.calory = calory;
        }

        public int type;
        public String text;
        public String calory;

        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text, String calory) {
            this.type = type;
            this.text = text;
            this.calory = calory;
        }
    }
}
