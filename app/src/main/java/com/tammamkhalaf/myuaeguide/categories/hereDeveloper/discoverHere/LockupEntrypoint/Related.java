
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Related {

    @SerializedName("recommended")
    @Expose
    private Recommended recommended;
    @SerializedName("public-transport")
    @Expose
    private PublicTransport publicTransport;

    public Recommended getRecommended() {
        return recommended;
    }

    public void setRecommended(Recommended recommended) {
        this.recommended = recommended;
    }

    public PublicTransport getPublicTransport() {
        return publicTransport;
    }

    public void setPublicTransport(PublicTransport publicTransport) {
        this.publicTransport = publicTransport;
    }

}
