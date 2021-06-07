package com.slow.adventure.ParksJSONData;

public class ParkData {
    String name, lon, lat;

    public ParkData(String name, String lon, String lat) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    public String getParkName() {
        return name;
    }

    public String getParkLon() {
        return lon;
    }

    public String getParkLat() {
        return lat;
    }
}

