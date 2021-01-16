
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item_ {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("average")
    @Expose
    private Integer average;
    @SerializedName("via")
    @Expose
    private Via_ via;
    @SerializedName("supplier")
    @Expose
    private Supplier_ supplier;
    @SerializedName("attribution")
    @Expose
    private String attribution;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    public Via_ getVia() {
        return via;
    }

    public void setVia(Via_ via) {
        this.via = via;
    }

    public Supplier_ getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier_ supplier) {
        this.supplier = supplier;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

}
