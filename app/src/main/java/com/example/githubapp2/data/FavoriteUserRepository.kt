package com.example.githubapp2.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubapp2.data.local.database.FavoriteUserEntity
import com.example.githubapp2.data.local.room.FavoriteUserDao
import com.example.githubapp2.data.local.room.FavoriteUserRoomDatabase
import com.example.githubapp2.data.remote.response.UserGithubResponse
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoriteUserRepository(application: Application) {

    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>> =
        mFavoriteUserDao.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(login: String): LiveData<FavoriteUserEntity> =
        mFavoriteUserDao.getFavoriteUserByUsername(login)

    fun insert(entity: FavoriteUserEntity) {
        executorService.execute { mFavoriteUserDao.insert(entity) }
    }

    fun delete(entity: FavoriteUserEntity) {
        executorService.execute { mFavoriteUserDao.delete(entity) }
    }

}