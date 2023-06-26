package com.example.client.factores;


import main.org.example.collection.*;

import java.util.ArrayList;

public class CityFactory {
    public City createCity(ArrayList<String> args) {
        String name = args.get(0);
        String[] coordinatesValues = {args.get(1), args.get(2)};
        Coordinates coordinates = new CoordinatesFactory().createCoordinates(coordinatesValues);
        Float area = Float.parseFloat(args.get(3));
        Long population = Long.parseLong(args.get(4));
        Double metersAboveSeaLevel = Double.parseDouble(args.get(5));
        Climate climate = Climate.valueOf(args.get(6));
        String human = args.get(9);
        Human newHuman = new HumanFactory().createHuman(human);
        Government government = null;
        StandardOfLiving standardOfLiving = null;
        if (!args.get(7).equals("")) {
            government = Government.valueOf(args.get(7));
        }
        if (!args.get(8).equals("")) {
            standardOfLiving = StandardOfLiving.valueOf(args.get(8));
        }

        City newCity = new City(0 , name , coordinates,area , population, metersAboveSeaLevel, climate, government, standardOfLiving, newHuman);
        return newCity;
    }
}
