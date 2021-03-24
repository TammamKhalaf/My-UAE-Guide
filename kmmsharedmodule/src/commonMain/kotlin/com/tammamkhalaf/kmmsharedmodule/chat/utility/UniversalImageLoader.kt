package com.tammamkhalaf.myuaeguide.chat.utility

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache

class UniversalImageLoader(context: android.content.Context) {
    private val mContext: android.content.Context// resource or drawable

    // resource or drawable
    // resource or drawable
    // UNIVERSAL IMAGE LOADER SETUP
    val config: ImageLoaderConfiguration
        get() {
            android.util.Log.d(TAG, "getConfig: Returning image loader configuration")
            // UNIVERSAL IMAGE LOADER SETUP
            val defaultOptions: DisplayImageOptions = Builder()
                    .showImageOnLoading(defaultImage) // resource or drawable
                    .showImageForEmptyUri(defaultImage) // resource or drawable
                    .showImageOnFail(defaultImage) // resource or drawable
                    .cacheOnDisk(true).cacheInMemory(true)
                    .cacheOnDisk(true).resetViewBeforeLoading(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(FadeInBitmapDisplayer(300)).build()
            return Builder(
                    mContext)
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(WeakMemoryCache())
                    .diskCacheSize(100 * 1024 * 1024)
                    .build()
        }

    companion object {
        private const val TAG = "UniversalImageLoader"
        private val defaultImage: Int = R.drawable.person
    }

    init {
        mContext = context
        android.util.Log.d(TAG, "UniversalImageLoader: started")
    }
}