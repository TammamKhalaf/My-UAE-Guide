package com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {

    @SerializedName("results")
    @Expose
    private List<Place> places = null;

    public List<Place> getResults() {
        return places;
    }

    public void setResults(List<Place> places) {
        this.places = places;
    }

}