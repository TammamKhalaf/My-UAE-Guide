package com.tammamkhalaf.myuaeguide.Categories.Hotels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("entities")
    @Expose
    private List<Entity> entities = null;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

}