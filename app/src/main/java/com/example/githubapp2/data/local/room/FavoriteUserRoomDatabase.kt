package com.example.githubapp2.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubapp2.data.local.database.FavoriteUserEntity
import com.example.githubapp2.data.remote.response.UserGithubResponse

@Database(entities = [FavoriteUserEntity::class], version = 2)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserRoomDatabase::class.java, "favorite_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoomDatabase
        }
    }
}