package com.example.githubapp2.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String? = null,
)
