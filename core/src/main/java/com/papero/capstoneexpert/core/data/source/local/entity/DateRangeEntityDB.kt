package com.papero.capstoneexpert.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "dateRangeROOM")
data class DateRangeEntityDB(
    @ColumnInfo(name = "maximum")
    val maximum: String,
    @ColumnInfo(name = "minimum")
    val minimum: String
)