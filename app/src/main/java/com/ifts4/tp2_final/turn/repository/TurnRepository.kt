package com.ifts4.tp2_final.turn.repository

import androidx.lifecycle.LiveData
import com.ifts4.tp2_final.data.database.DB
import com.ifts4.tp2_final.data.entities.TurnEntity

class TurnRepository {

    private val turnDao = DB.getDatabase().turnDao()

    suspend fun insertTurn(turn: TurnEntity) {
        turnDao.insertTurn(turn)
    }

    fun readAllTurnFromTheUser(dniUserOwner: Long): LiveData<List<TurnEntity>> {
        val data = turnDao.readAllTurnFromTheUser(dniUserOwner)
        return data
    }

    suspend fun deleteTurn(turn: TurnEntity) {
        turnDao.deleteTurn(turn)
    }

    fun selectTurnById(idTurn: Int): TurnEntity {
        val turnEntityData = turnDao.selectTurnById(idTurn)
        return turnEntityData
    }

    fun countAllTurnsFromTheUser(dniUserOwner: Long): List<LiveData<List<TurnEntity>>> {
        val data = turnDao.readAllTurnFromTheUser(dniUserOwner)
        return listOf(data)
    }

}