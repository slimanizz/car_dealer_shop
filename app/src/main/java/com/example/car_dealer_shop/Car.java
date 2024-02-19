package com.example.car_dealer_shop;

import java.util.ArrayList;

public class Car {
    public static ArrayList<Car> allCars = new ArrayList<>();
    int id;
    String type;

    public Car(String type, int id) {
        this.type = type;
        this.id = id;
    }

    public Car() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", id=" + id +
                '}';
    }
}
