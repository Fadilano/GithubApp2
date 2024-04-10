package com.example.githubapp2.data.remote.retrofit

import com.example.githubapp2.data.remote.response.GithubResponses
import com.example.githubapp2.data.remote.response.UserDetailResponse
import com.example.githubapp2.data.remote.response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUsers(
        @Query("q") query: String,
    ): Call<GithubResponses>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserGithubResponse>>

    @GET("users/{username}/following")
    fun getFollowings(
        @Path("username") username: String
    ): Call<List<UserGithubResponse>>
}