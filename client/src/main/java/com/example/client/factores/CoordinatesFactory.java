package com.example.client.factores;

import main.org.example.collection.Coordinates;

public class CoordinatesFactory {
    public Coordinates createCoordinates(String[] args) {
        Integer x = Integer.parseInt(args[0]);
        Integer y = Integer.parseInt(args[1]);
        Coordinates newCoordinates = new Coordinates(x , y);
        return newCoordinates;
    }
}
