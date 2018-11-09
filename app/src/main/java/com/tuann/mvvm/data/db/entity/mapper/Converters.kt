package com.tuann.mvvm.data.db.entity.mapper

import androidx.room.TypeConverter
import org.threeten.bp.Instant

object Converters {
    @JvmStatic
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? =
        if (value == null) {
            null
        } else {
            Instant.ofEpochSecond(value)
        }

    @JvmStatic
    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? =
        date?.epochSecond
}