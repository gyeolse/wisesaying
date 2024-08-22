package com.example.androidsample.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidsample.data.datasource.dao.TodoDao
import com.example.androidsample.data.datasource.dao.WiseSayingDao
import com.example.androidsample.data.model.WiseSaying

@Database(entities = [WiseSaying::class], version = 1)
abstract class WiseSayingDatabase : RoomDatabase() {
    abstract fun wiseSayingDao() : WiseSayingDao

    companion object {
        @Volatile
        private var INSTANCE: WiseSayingDatabase? = null

        fun getDatabase(context: Context): WiseSayingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WiseSayingDatabase::class.java,
                    "wisesaying"
                )
                .createFromAsset("wisesaying.db")
                .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
