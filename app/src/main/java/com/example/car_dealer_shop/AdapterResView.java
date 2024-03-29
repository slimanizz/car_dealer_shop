package com.example.car_dealer_shop;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

class AdapterResView extends RecyclerView.Adapter<AdapterResView.ListViewHolder> {

    private  ArrayList<String> infos;
    Context context;


    public AdapterResView(Context context,ArrayList<String> infos){
        this.context = context;
        this.infos = infos;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_for_admin_item_list,viewGroup,false);
        return new ListViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int i) {
        final int carid = i;
        listViewHolder.textView.setText(infos.get(i));
    }
    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;

        public ListViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.reserve_text_in_list_item_for_admin);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}