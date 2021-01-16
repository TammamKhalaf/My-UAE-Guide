
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("supports")
    @Expose
    private List<String> supports = null;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("via")
    @Expose
    private Via via;
    @SerializedName("supplier")
    @Expose
    private Supplier supplier;
    @SerializedName("attribution")
    @Expose
    private String attribution;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getSupports() {
        return supports;
    }

    public void setSupports(List<String> supports) {
        this.supports = supports;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Via getVia() {
        return via;
    }

    public void setVia(Via via) {
        this.via = via;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

}
