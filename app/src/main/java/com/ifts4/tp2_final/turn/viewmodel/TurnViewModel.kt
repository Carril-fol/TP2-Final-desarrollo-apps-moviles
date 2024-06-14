package com.ifts4.tp2_final.turn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifts4.tp2_final.data.entities.TurnEntity
import com.ifts4.tp2_final.turn.repository.TurnRepository
import kotlinx.coroutines.launch

class TurnViewModel(): ViewModel() {

    // Instance repository
    private val repository = TurnRepository()

    // Funcs
    fun insertTurn(turn: TurnEntity) {
        viewModelScope.launch {
            repository.insertTurn(turn)
        }
    }

    fun readAllTurnFromTheUser(dniUserOwner: Long): LiveData<List<TurnEntity>> {
        val data = repository.readAllTurnFromTheUser(dniUserOwner)
        return data
    }

    fun selectTurnById(idTurn: Int): TurnEntity {
        val turnEntityData = repository.selectTurnById(idTurn)
        return turnEntityData
    }

    fun deleteTurn(turn: TurnEntity) {
        viewModelScope.launch {
            repository.deleteTurn(turn)
        }
    }

    fun countFromAllTurnsFromTheUser(dniUserOwner: Long): List<LiveData<List<TurnEntity>>> {
        val data = repository.countAllTurnsFromTheUser(dniUserOwner)
        return data
    }

}