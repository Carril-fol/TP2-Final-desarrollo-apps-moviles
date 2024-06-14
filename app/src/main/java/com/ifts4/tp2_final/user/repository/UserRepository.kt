package com.ifts4.tp2_final.user.repository

import androidx.lifecycle.LiveData
import com.ifts4.tp2_final.data.database.DB
import com.ifts4.tp2_final.data.entities.UserEntity

class UserRepository {

    private val userDao = DB.getDatabase().userDao()

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    fun selectUserFilterByEmailAndPassword(emailUser: String, passwordUser: String): UserEntity {
        return userDao.selectUserFilterByEmailAndPassword(emailUser, passwordUser)
    }

    fun selectUserFilterByDni(dniUser: Long): UserEntity {
        return userDao.selectUserFilterByDni(dniUser)
    }

    fun selectCountUserFilterByDni(dniUser: Long): Int{
        return userDao.selectCountUserFilterByDni(dniUser)
    }

}