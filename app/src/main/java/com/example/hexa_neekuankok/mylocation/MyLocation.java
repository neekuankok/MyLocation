package com.example.hexa_neekuankok.mylocation;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by HI-KOKNEE.KUAN on 16/11/2016.
 */
//@Singleton
public class MyLocation implements Serializable{
    private double longitude;
    private double latitude;
    private String strLongitude;
    private String strLatitude;
    private String strLocationAvailability;

    public String getStrLongitude() {
        return strLongitude;
    }

    public void setStrLongitude(String strLongitude) {
        this.strLongitude = strLongitude;
    }

    public String getStrLatitude() {
        return strLatitude;
    }

    public void setStrLatitude(String strLatitude) {
        this.strLatitude = strLatitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //@Inject
    public MyLocation(){

        longitude = 0.0;
        strLongitude = Double.toString(longitude);
        latitude = 0.0;
        strLatitude = Double.toString(latitude);
        strLocationAvailability = "Not Available";
    }

    public String getStrLocationAvailability() {
        return strLocationAvailability;
    }

    public void setStrLocationAvailability(String strLocationAvailability) {
        this.strLocationAvailability = strLocationAvailability;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
