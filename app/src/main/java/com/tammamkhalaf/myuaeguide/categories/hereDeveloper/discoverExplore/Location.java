
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("position")
    @Expose
    private List<Double> position = null;
    @SerializedName("address")
    @Expose
    private Address address;

    public List<Double> getPosition() {
        return position;
    }

    public void setPosition(List<Double> position) {
        this.position = position;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
