
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("placeId")
    @Expose
    private String placeId;
    @SerializedName("view")
    @Expose
    private String view;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("contacts")
    @Expose
    private Contacts contacts;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("related")
    @Expose
    private Related related;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Related getRelated() {
        return related;
    }

    public void setRelated(Related related) {
        this.related = related;
    }

}
