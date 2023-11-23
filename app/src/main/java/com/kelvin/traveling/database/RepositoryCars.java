package com.kelvin.traveling.database;

import com.kelvin.traveling.R;
import com.kelvin.traveling.features.home.domain.Car;
import com.kelvin.traveling.features.home.domain.FeatureCar;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryCars {

    private RepositoryCars() {
        //Utility class
    }

    public static List<Car> getListCar() {
        List<Car> carArrayList = new ArrayList<>();
        carArrayList.add(new Car("Classic Car", 34.0f, new FeatureCar(R.color.classicCar, R.drawable.img_vehicle_classic_car), false));
        carArrayList.add(new Car("Sport Car", 55.0f, new FeatureCar(R.color.sportCar, R.drawable.img_vehicle_sport_car), false));
        carArrayList.add(new Car("Flying Car", 500.0f, new FeatureCar(R.color.flyingCar, R.drawable.img_vehicle_flying_car), false));
        carArrayList.add(new Car("Electric Car", 45.0f, new FeatureCar(R.color.electricCar, R.drawable.img_vehicle_electric_car), false));
        carArrayList.add(new Car("Motorhome", 23.0f, new FeatureCar(R.color.motorhomeCar, R.drawable.img_vehicle__motor_home), false));
        carArrayList.add(new Car("PickUp", 10.0f, new FeatureCar(R.color.pickUpCar, R.drawable.img_vehicle_pick_up_car), false));
        carArrayList.add(new Car("AirPlane", 11.0f, new FeatureCar(R.color.airPlane, R.drawable.img_vehicle_air_plain), false));
        carArrayList.add(new Car("Bus", 14.0f, new FeatureCar(R.color.busCar, R.drawable.img_vehicle_bus), false));
        return carArrayList;

    }
}
