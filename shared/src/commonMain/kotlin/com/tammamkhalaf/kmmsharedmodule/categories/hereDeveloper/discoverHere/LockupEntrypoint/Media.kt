package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Media {
    @SerializedName("images")
    @Expose
    private var images: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Images? = null

    @SerializedName("reviews")
    @Expose
    private var reviews: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Reviews? = null

    @SerializedName("ratings")
    @Expose
    private var ratings: Ratings? = null
    fun getImages(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Images? {
        return images
    }

    fun setImages(images: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Images?) {
        this.images = images
    }

    fun getReviews(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Reviews? {
        return reviews
    }

    fun setReviews(reviews: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Reviews?) {
        this.reviews = reviews
    }

    fun getRatings(): Ratings? {
        return ratings
    }

    fun setRatings(ratings: Ratings?) {
        this.ratings = ratings
    }
}