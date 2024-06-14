package com.ifts4.tp2_final.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "turns_table",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("dni"),
            childColumns = arrayOf("dniUserOwner"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TurnEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "dateApointment") val dateApointment: Long,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "dniUserOwner") val userOwnerId: Long
): Serializable