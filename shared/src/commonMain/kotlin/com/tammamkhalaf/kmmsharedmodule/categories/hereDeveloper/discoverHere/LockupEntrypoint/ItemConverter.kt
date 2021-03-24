package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import androidx.annotation.RequiresApi

class ItemConverter {
    @RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @TypeConverter
    fun fromObjectToGson(item: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Item?): String {
        return Gson().toJson(item)
    }

    @RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @TypeConverter
    fun fromGsonToObject(str: String?): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Item {
        return Gson().fromJson(str, com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Item::class.java)
    }
}