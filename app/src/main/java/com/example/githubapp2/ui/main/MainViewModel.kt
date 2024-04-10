package com.example.githubapp2.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp2.data.remote.response.GithubResponses
import com.example.githubapp2.data.remote.response.UserGithubResponse
import com.example.githubapp2.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<UserGithubResponse>>()
    val listUser: LiveData<List<UserGithubResponse>> = _listUser

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUsers()
    }


    private fun getUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(MainActivity.query)
        client.enqueue(object : Callback<GithubResponses> {
            override fun onResponse(
                call: Call<GithubResponses>,
                response: Response<GithubResponses>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.items
                    }
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponses>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
            }
        })

    }

    fun getUsersSearch(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<GithubResponses> {
            override fun onResponse(
                call: Call<GithubResponses>,
                response: Response<GithubResponses>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.items
                    }
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponses>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
            }
        })

    }
}