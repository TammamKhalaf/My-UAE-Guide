
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "PlacesTable")
public class PlaceResponse{

    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("placeId")
    @Expose
    private String placeId;
    @SerializedName("view")
    @Expose
    private String view;

    @SerializedName("icon")
    @Expose
    private String icon;

    @Ignore
    @SerializedName("location")
    @Expose
    private Location location;

    @Ignore
    @SerializedName("contacts")
    @Expose
    private Contacts contacts;

    @Ignore
    @SerializedName("categories")
    @Expose
    private List<Category> categories;

    @Ignore
    @SerializedName("media")
    @Expose
    private Media media;


    @SerializedName("extended")
    @Expose
    @Ignore
    private Extended extended;

    @Ignore
    @SerializedName("related")
    @Expose
    private Related related;

    public PlaceResponse(String name, String placeId, String view, Location location, Contacts contacts,
                         List<Category> categories, String icon, Media media, Extended extended, Related related) {
        this.name = name;
        this.placeId = placeId;
        this.view = view;
        this.location = location;
        this.contacts = contacts;
        this.categories = categories;
        this.icon = icon;
        this.media = media;
        this.extended = extended;
        this.related = related;
    }

    public PlaceResponse() {
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Extended getExtended() {
        return extended;
    }

    public void setExtended(Extended extended) {
        this.extended = extended;
    }

    public Related getRelated() {
        return related;
    }

    public void setRelated(Related related) {
        this.related = related;
    }

    public int getId() {
        return id;
    }
}
