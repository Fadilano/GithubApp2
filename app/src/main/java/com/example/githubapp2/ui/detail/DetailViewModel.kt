package com.example.githubapp2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp2.data.remote.response.UserDetailResponse
import com.example.githubapp2.data.remote.response.UserGithubResponse
import com.example.githubapp2.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

class DetailViewModel : ViewModel() {
    private val _UserDetail = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _UserDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFollowLoading = MutableLiveData<Boolean>()
    val isFollowLoading: LiveData<Boolean> = _isFollowLoading

    private val _followerList = MutableLiveData<List<UserGithubResponse>>()
    val followerList: LiveData<List<UserGithubResponse>> = _followerList

    private val _followingList = MutableLiveData<List<UserGithubResponse>>()
    val followingList: LiveData<List<UserGithubResponse>> = _followingList


    var username: String = ""
        set(value) {
            field = value
            getUserDetail()
            getFollowerList()
            getFollowingList()
        }

    private fun getUserDetail() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _UserDetail.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun getFollowerList() {
        _isFollowLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<UserGithubResponse>> {
            override fun onResponse(
                call: Call<List<UserGithubResponse>>,
                response: Response<List<UserGithubResponse>>
            ) {
                _isFollowLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _followerList.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserGithubResponse>>, t: Throwable) {
                _isFollowLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

    }

    private fun getFollowingList() {
        _isFollowLoading.value = true
        val client = ApiConfig.getApiService().getFollowings(username)
        client.enqueue(object : Callback<List<UserGithubResponse>> {
            override fun onResponse(
                call: Call<List<UserGithubResponse>>,
                response: Response<List<UserGithubResponse>>
            ) {
                _isFollowLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _followingList.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserGithubResponse>>, t: Throwable) {
                _isFollowLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

    }


    companion object {
        private const val TAG = "DetailViewModel"
    }


}