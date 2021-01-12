
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("position")
    @Expose
    private List<String> position = null;

    public List<String> getPosition() {
        return position;
    }

    public void setPosition(List<String> position) {
        this.position = position;
    }

}
