package com.ferhatt.workoutapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase:RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {

        @Volatile
        private var INSTANCE: HistoryDatabase? = null


        fun getInstance(context: Context): HistoryDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance


            }


        }

    }
}