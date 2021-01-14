
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search {

    @SerializedName("context")
    @Expose
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
