package com.ifts4.tp2_final.user.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ifts4.tp2_final.data.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users_table WHERE email = :emailUser AND password = :passwordUser")
    fun selectUserFilterByEmailAndPassword(emailUser: String, passwordUser: String): UserEntity

    @Query("SELECT * FROM users_table WHERE dni = :userDni")
    fun selectUserFilterByDni(userDni: Long): UserEntity

    @Query("SELECT COUNT(*) FROM users_table WHERE dni = :userDni")
    fun selectCountUserFilterByDni(userDni: Long): Int
}