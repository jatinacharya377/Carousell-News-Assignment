package com.carousell.carousellnews.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * This object class is responsible for parsing our json file to gson.
 * @author: Jagannath Acharya
 */
object GsonUtils {
    /**
     * This function is responsible for converting our json to gson/data class.
     * @param: json as String
     * @return: T (type T which means it could be of any data type)
     * @author: Jagannath Acharya
     */
    inline fun <reified T> jsonToGson(json: String?): T {
        val gson = GsonBuilder().serializeNulls().create()
        val type: Type = object : TypeToken<T>() {}.type
        return gson.fromJson(
            json,
            type
        )
    }
}