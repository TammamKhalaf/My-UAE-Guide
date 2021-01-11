package com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured

import android.util.Log

class FeaturedHelperClass {
   var imageUrl: String
    var title: String?
    var description: String?

    constructor(imageUrl: String, title: String?, description: String?) {
        this.imageUrl = imageUrl
        this.title = title
        this.description = description
        Log.d(TAG, ": $imageUrl--->$title--->$description")
    }

    companion object {
        private const val TAG = "FeaturedHelperClass"
    }


}

