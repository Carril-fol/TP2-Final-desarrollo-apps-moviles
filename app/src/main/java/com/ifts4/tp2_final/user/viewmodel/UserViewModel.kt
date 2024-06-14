package com.ifts4.tp2_final.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.ifts4.tp2_final.data.entities.UserEntity
import com.ifts4.tp2_final.user.repository.UserRepository


class UserViewModel() : ViewModel() {

    // Instance repository
    private val repository = UserRepository()

    // Funcs
    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun selectUserFilterByEmailAndPassword(emailUser: String, passwordUser: String): UserEntity {
        return repository.selectUserFilterByEmailAndPassword(emailUser, passwordUser)
    }

    fun selectUserFilterByDni(dniUser: Long): UserEntity {
        return repository.selectUserFilterByDni(dniUser)
    }

    fun selectCountUserFilterByDni(dniUser: Long): Int {
        return repository.selectCountUserFilterByDni(dniUser)
    }

}