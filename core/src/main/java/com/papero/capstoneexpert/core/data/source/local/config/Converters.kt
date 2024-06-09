package com.papero.capstoneexpert.core.data.source.local.config

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromArrayList(list: ArrayList<Int>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toArrayList(data: String): ArrayList<Int> {
        return ArrayList(data.split(",").map { it.toInt() })
    }
}