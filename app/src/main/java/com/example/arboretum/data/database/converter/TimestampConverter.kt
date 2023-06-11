package com.example.arboretum.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.sql.Date

@ProvidedTypeConverter
class TimestampConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}