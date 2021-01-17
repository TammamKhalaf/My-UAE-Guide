package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ItemConverter {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public String fromObjectToGson(Item item){
        return new Gson().toJson(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public Item fromGsonToObject(String str){
        return new Gson().fromJson(str,Item.class);
    }

}
