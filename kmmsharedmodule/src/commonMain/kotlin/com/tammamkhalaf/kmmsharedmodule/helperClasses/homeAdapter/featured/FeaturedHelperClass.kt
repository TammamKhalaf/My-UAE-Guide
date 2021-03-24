package com.tammamkhalaf.kmmsharedmodule.helperClasses.homeAdapter.featured

import android.util.Log

class FeaturedHelperClass {

    var imageUrl: String
    var title: String?
    var description: String?
    var id:String?

    constructor(imageUrl: String, title: String?, description: String?, id: String) {
        this.imageUrl = imageUrl
        this.title = title
        this.description = description
        this.id = id
    }

    companion object {
        private const val TAG = "FeaturedHelperClass"
    }


}

