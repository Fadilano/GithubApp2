package com.example.githubapp2.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp2.data.FavoriteUserRepository
import com.example.githubapp2.data.local.database.FavoriteUserEntity
import com.example.githubapp2.data.remote.response.UserGithubResponse

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun insert(entity: FavoriteUserEntity) {
        mFavoriteUserRepository.insert(entity)
    }

    fun delete(entity: FavoriteUserEntity) {
        mFavoriteUserRepository.delete(entity)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>> =
        mFavoriteUserRepository.getAllFavoriteUsers()

    fun getFavoriteUsersbyUsername(login: String): LiveData<FavoriteUserEntity> =
        mFavoriteUserRepository.getFavoriteUserByUsername(login)
}