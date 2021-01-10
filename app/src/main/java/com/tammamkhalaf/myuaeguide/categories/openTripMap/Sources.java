package com.tammamkhalaf.myuaeguide.categories.openTripMap;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sources {

    @SerializedName("geometry")
    @Expose
    private String geometry;
    @SerializedName("attributes")
    @Expose
    private List<String> attributes = null;

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

}