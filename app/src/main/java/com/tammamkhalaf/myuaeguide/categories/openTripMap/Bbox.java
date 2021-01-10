package com.tammamkhalaf.myuaeguide.categories.openTripMap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bbox {

    @SerializedName("lat_max")
    @Expose
    private Double latMax;
    @SerializedName("lat_min")
    @Expose
    private Double latMin;
    @SerializedName("lon_max")
    @Expose
    private Double lonMax;
    @SerializedName("lon_min")
    @Expose
    private Double lonMin;

    public Double getLatMax() {
        return latMax;
    }

    public void setLatMax(Double latMax) {
        this.latMax = latMax;
    }

    public Double getLatMin() {
        return latMin;
    }

    public void setLatMin(Double latMin) {
        this.latMin = latMin;
    }

    public Double getLonMax() {
        return lonMax;
    }

    public void setLonMax(Double lonMax) {
        this.lonMax = lonMax;
    }

    public Double getLonMin() {
        return lonMin;
    }

    public void setLonMin(Double lonMin) {
        this.lonMin = lonMin;
    }

}