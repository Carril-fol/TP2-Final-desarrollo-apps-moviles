package com.ifts4.tp2_final.data.converters

import androidx.room.TypeConverter
import java.util.Calendar

class Converters {

    @TypeConverter
    fun dateToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun fromTimestamp(value: Long): Calendar? {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = value
        }
        return calendar
    }

}