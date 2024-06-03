package com.dicoding.storyviewapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.storyviewapp.data.remote.response.ListStoryItem
import com.dicoding.storyviewapp.data.remote.response.RemoteKey

@Database(
    entities = [ListStoryItem::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class StoryRoomDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    companion object {
        @Volatile
        private var INSTANCE: StoryRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryRoomDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}