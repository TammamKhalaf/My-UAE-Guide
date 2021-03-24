package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import com.google.gson.annotations.Expose

class AlternativeName {
    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("language")
    @Expose
    private var language: String? = null
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getLanguage(): String? {
        return language
    }

    fun setLanguage(language: String?) {
        this.language = language
    }
}