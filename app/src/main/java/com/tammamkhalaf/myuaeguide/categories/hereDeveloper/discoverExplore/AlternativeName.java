
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlternativeName {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("language")
    @Expose
    private String language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
