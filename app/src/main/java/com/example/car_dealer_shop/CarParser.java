package com.example.car_dealer_shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarParser {

    public static List<Car> getObjectFromJason(String jason) {
        List<Car> cars;
        try {
            JSONArray jsonArray = new JSONArray(jason);
            cars = new ArrayList<>();
            for(int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject= (JSONObject) jsonArray.get(i);
                Car car = new Car();
                car.setId(i);
                car.setType(jsonObject.getString("type"));
                cars.add(car);
                Car.allCars.add(car);
            }
        } catch (JSONException e)
        { e.printStackTrace();
            return null;
        }
        return cars;
    }
}
