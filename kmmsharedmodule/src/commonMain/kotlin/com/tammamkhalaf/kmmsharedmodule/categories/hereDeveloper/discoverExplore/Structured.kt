package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import com.google.gson.annotations.Expose

class Structured {
    @SerializedName("start")
    @Expose
    private var start: String? = null

    @SerializedName("duration")
    @Expose
    private var duration: String? = null

    @SerializedName("recurrence")
    @Expose
    private var recurrence: String? = null
    fun getStart(): String? {
        return start
    }

    fun setStart(start: String?) {
        this.start = start
    }

    fun getDuration(): String? {
        return duration
    }

    fun setDuration(duration: String?) {
        this.duration = duration
    }

    fun getRecurrence(): String? {
        return recurrence
    }

    fun setRecurrence(recurrence: String?) {
        this.recurrence = recurrence
    }
}