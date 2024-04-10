package com.example.githubapp2.data.local.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUserEntity(

    @PrimaryKey
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String,

    @ColumnInfo(name = "avatarUrl")
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null
) : Parcelable

