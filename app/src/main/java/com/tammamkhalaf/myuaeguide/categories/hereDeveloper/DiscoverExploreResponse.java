
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoverExploreResponse {

    @SerializedName("search")
    @Expose
    private Search search;
    @SerializedName("results")
    @Expose
    private Results results;

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

}
