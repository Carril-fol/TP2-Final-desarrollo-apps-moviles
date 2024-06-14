package com.ifts4.tp2_final.turn.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ifts4.tp2_final.data.entities.TurnEntity

@Dao
interface TurnDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTurn(turn: TurnEntity)

    @Query("SELECT * FROM turns_table WHERE dniUserOwner = :dniUserOwner")
    fun readAllTurnFromTheUser(dniUserOwner: Long): LiveData<List<TurnEntity>>

    @Query("SELECT * FROM turns_table WHERE id = :idTurn")
    fun selectTurnById(idTurn: Int): TurnEntity

    @Delete
    fun deleteTurn(turn: TurnEntity)

    // TODO: Agregar funcion para updatear

    // TODO: Agregar funcion para borrar turnos
}