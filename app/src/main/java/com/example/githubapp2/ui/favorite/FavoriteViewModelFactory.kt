package com.example.githubapp2.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp2.data.FavoriteUserRepository

class FavoriteViewModelFactory(private val mApplication: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}