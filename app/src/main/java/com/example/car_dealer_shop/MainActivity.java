package com.example.car_dealer_shop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button connectButton;
    ArrayList<Car> carListFromRest = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectButton = findViewById(R.id.connectToRestButton);
        connectButton.setOnClickListener(v -> {
            ConnectionAsyncTask connectionAsyncTask= new ConnectionAsyncTask(MainActivity.this);
            connectionAsyncTask.execute("https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types");
        });
    }
    public void fillCarList(ArrayList<Car> list) {
        if (list == null) {
            Toast.makeText(this, "Error Connecting to API",
                    Toast.LENGTH_LONG).show();
        } else {
            carListFromRest = list;
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.deleteAllCars();
            for (Car car : carListFromRest) dataBaseHelper.insertCar(car);
            Intent myIntent=new Intent(MainActivity.this,
                    LoginActivity.class);
            MainActivity.this.startActivity(myIntent);

        }
    }
}