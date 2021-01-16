
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extended {

    @SerializedName("openingHours")
    @Expose
    private OpeningHours openingHours;

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

}
