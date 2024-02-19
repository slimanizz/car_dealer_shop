package com.example.car_dealer_shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ListViewHolder> {

    private ArrayList<Pair<Integer,Car>> cars;
    Context context;
    public OffersAdapter(Context context,ArrayList<Pair<Integer,Car>> cars){
        this.context = context;
        this.cars = cars;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.special_offer_list_item,viewGroup,false);
        return new ListViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int i) {
        final int carid = i;
        Car car = Car.allCars.get(carid);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn", "");

        String details = "";

        // Assuming Car class has fields like "id," "type,"
        details += "ID: " + car.getId() + "\n";
        details += "Type: " + car.getType() + "\n";
        listViewHolder.textView.setText(details);

        listViewHolder.button1.setOnClickListener(v -> {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            dataBaseHelper.insertReserve(name, car.getId(), dtf.format(now));
        });

        listViewHolder.button2.setOnClickListener(v -> {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            dataBaseHelper.insertFavorite(name, car.getId());
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ImageButton button1;
        public ImageButton button2;
        public ListViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.special_offers_text);
            button2 = itemView.findViewById(R.id.fav_button_menu_offers);
            button1 = itemView.findViewById(R.id.reserve_button_menu_offers);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
        }
    }
}
