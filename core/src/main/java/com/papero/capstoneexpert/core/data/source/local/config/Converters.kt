package com.papero.capstoneexpert.core.data.source.local.config

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//class Converters {
//    @TypeConverter
//    fun fromListInt(value: List<Int>): String {
//        return Gson().toJson(value)
//    }
//
//    @TypeConverter
//    fun toListInt(value: String): List<Int> {
//        val listType = object : TypeToken<List<Int>>() {}.type
//        return Gson().fromJson(value, listType)
//    }
//}