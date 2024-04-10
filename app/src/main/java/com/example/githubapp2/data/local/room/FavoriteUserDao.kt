package com.example.githubapp2.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubapp2.data.local.database.FavoriteUserEntity
import com.example.githubapp2.data.remote.response.UserGithubResponse

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: FavoriteUserEntity)

    @Delete
    fun delete(entity: FavoriteUserEntity)

    @Query("SELECT * from FavoriteUserEntity ORDER BY login ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM FavoriteUserEntity WHERE login = :login")
    fun getFavoriteUserByUsername(login: String): LiveData<FavoriteUserEntity>
}