package com.ifts4.tp2_final.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ifts4.tp2_final.TpAppClass
import com.ifts4.tp2_final.data.converters.Converters
import com.ifts4.tp2_final.data.entities.TurnEntity

import com.ifts4.tp2_final.data.entities.UserEntity
import com.ifts4.tp2_final.turn.dao.TurnDao
import com.ifts4.tp2_final.user.dao.UserDao

@Database(entities = [UserEntity::class, TurnEntity::class], version = 5)
abstract class DB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun turnDao(): TurnDao

    companion object {
        @Volatile
        private var INSTANCE: DB? = null

        fun getDatabase(): DB {
            val tempInstance = INSTANCE

            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    TpAppClass.instance.applicationContext,
                    DB::class.java,
                    "momentum"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }

}