package com.example.spingus.pointtopointplus;

/**
 * Created by Spingus on 29/07/2017.
 */

public class Locations {
    public Double pointAlon = 0.0;
    public Double pointAlat = 0.0;
    public Double pointBlon = 0.0;
    public Double pointBlat = 0.0;

    Locations(Double alon, Double alat, Double blon, Double blat){
        this.pointAlon = alon;
        this.pointAlat = alat;
        this.pointBlon = blon;
        this.pointBlat = blat;
    }

    public void updateA(Double alon, Double alat){
        this.pointAlon = alon;
        this.pointAlat = alat;
        Nav.originLon = alon;
        Nav.originLat = alat;

    }
    public void updateB(Double blon, Double blat){
        this.pointBlon = blon;
        this.pointBlat = blat;
        Nav.destinationLon = blon;
        Nav.destinationLat = blat;
    }
}
