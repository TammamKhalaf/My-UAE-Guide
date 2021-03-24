package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Contacts {
    @SerializedName("phone")
    @Expose
    private var phone: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Phone>? = null

    @SerializedName("email")
    @Expose
    private var email: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Email>? = null

    @SerializedName("website")
    @Expose
    private var website: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Website>? = null
    fun getPhone(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Phone>? {
        return phone
    }

    fun setPhone(phone: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Phone>?) {
        this.phone = phone
    }

    fun getEmail(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Email>? {
        return email
    }

    fun setEmail(email: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Email>?) {
        this.email = email
    }

    fun getWebsite(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Website>? {
        return website
    }

    fun setWebsite(website: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Website>?) {
        this.website = website
    }
}