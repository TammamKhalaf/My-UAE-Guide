
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoverExploreResponse {

    @SerializedName("results")
    @Expose
    private Results results;
    @SerializedName("search")
    @Expose
    private Search search;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

}
