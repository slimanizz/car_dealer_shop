package com.example.car_dealer_shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
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

class FavortieAdapter extends RecyclerView.Adapter<FavortieAdapter.ListViewHolder> {

    private ArrayList<Car> cars;
    Context context;


    public FavortieAdapter(Context context,ArrayList<Car> cars){
        this.context = context;
        this.cars = cars;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_list_item,viewGroup,false);
        return new ListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn", "");
        final int carId = i;

        listViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));

                int newId = -1;
                for (int x = 0; x < Car.allCars.size(); x++) {
                    if (cars.get(carId).getType().equals(Car.allCars.get(x).getType())) {
                        newId = Car.allCars.get(x).getId();
                        break;
                    }
                }

                if (newId != -1) {
                    dataBaseHelper.insertReserve(name, newId, dtf.format(now));
                    System.out.println(name + " " + newId + " " + cars.get(carId).getType());
                } else {
                    // Handle the case where the corresponding Car object is not found
                    // You might want to show an error message or log something
                    System.out.println("Car not found for type: " + cars.get(carId).getType());
                }
            }
        });

        listViewHolder.textView.setText(cars.get(i).getId() + " " + cars.get(i).getType());
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        ImageButton imageButton;
        public ListViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.car_name_in_fav_list_item);
            imageButton = (ImageButton)itemView.findViewById(R.id.reserve_button_in_fav_list);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}