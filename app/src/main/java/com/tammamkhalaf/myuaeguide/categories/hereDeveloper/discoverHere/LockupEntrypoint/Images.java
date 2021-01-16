
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("available")
    @Expose
    private Integer available;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
