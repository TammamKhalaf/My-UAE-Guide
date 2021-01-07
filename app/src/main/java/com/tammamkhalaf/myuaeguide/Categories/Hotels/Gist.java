package com.tammamkhalaf.myuaeguide.Categories.Hotels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gist{

    @SerializedName("term")
    @Expose
    private String term;
    @SerializedName("moresuggestions")
    @Expose
    private Integer moresuggestions;
    @SerializedName("autoSuggestInstance")
    @Expose
    private Object autoSuggestInstance;
    @SerializedName("trackingID")
    @Expose
    private String trackingID;
    @SerializedName("misspellingfallback")
    @Expose
    private Boolean misspellingfallback;
    @SerializedName("suggestions")
    @Expose
    private List<Suggestion> suggestions = null;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getMoresuggestions() {
        return moresuggestions;
    }

    public void setMoresuggestions(Integer moresuggestions) {
        this.moresuggestions = moresuggestions;
    }

    public Object getAutoSuggestInstance() {
        return autoSuggestInstance;
    }

    public void setAutoSuggestInstance(Object autoSuggestInstance) {
        this.autoSuggestInstance = autoSuggestInstance;
    }

    public String getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(String trackingID) {
        this.trackingID = trackingID;
    }

    public Boolean getMisspellingfallback() {
        return misspellingfallback;
    }

    public void setMisspellingfallback(Boolean misspellingfallback) {
        this.misspellingfallback = misspellingfallback;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

}