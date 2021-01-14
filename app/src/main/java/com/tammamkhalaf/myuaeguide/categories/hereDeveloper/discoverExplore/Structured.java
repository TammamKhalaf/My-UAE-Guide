
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Structured {

    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("recurrence")
    @Expose
    private String recurrence;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

}
