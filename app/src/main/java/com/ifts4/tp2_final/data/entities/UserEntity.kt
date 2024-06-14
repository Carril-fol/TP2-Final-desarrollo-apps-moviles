package com.ifts4.tp2_final.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users_table")
data class UserEntity (
    @PrimaryKey @ColumnInfo(name = "dni") val dni: Long,
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
): Serializable