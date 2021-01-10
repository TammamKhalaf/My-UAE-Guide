package com.tammamkhalaf.myuaeguide.categories.openTripMap;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geoname {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("status")
    @Expose
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}