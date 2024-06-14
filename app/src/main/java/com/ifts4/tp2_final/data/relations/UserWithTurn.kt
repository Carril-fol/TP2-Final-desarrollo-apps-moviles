package com.ifts4.tp2_final.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ifts4.tp2_final.data.entities.TurnEntity
import com.ifts4.tp2_final.data.entities.UserEntity

data class UserWithTurn (
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "dni",
        entityColumn = "dniUserOwner"
    )
    val turns: List<TurnEntity>
)