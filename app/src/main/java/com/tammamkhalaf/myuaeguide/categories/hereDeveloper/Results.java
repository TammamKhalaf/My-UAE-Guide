
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {

    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
