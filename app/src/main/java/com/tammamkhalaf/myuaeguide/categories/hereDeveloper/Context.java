
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Context {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("href")
    @Expose
    private String href;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
