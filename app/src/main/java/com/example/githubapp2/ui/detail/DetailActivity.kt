package com.example.githubapp2.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapp2.R
import com.example.githubapp2.data.local.database.FavoriteUserEntity
import com.example.githubapp2.data.remote.response.UserDetailResponse
import com.example.githubapp2.data.remote.response.UserGithubResponse
import com.example.githubapp2.databinding.ActivityDetailBinding
import com.example.githubapp2.ui.SectionsPagerAdapter
import com.example.githubapp2.ui.favorite.FavoriteViewModel
import com.example.githubapp2.ui.favorite.FavoriteViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var isFavorite: Boolean = false


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        favoriteViewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(application)
        )[FavoriteViewModel::class.java]

        val user = intent.getParcelableExtra<UserGithubResponse>(EXTRA_USER) as UserGithubResponse
        val favUser = intent.getParcelableExtra<FavoriteUserEntity>(EXTRA_FAV_USER)


        val login = user.login
        val avatarUrl = user.avatarUrl


        detailViewModel.username = login

        detailViewModel.detailUser.observe(this) {
            setUserDetail(it)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        favoriteViewModel.getFavoriteUsersbyUsername(login).observe(this) { favoriteUser ->
            isFavorite = favoriteUser != null
            isFavoriteUser(login, avatarUrl )
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user.login
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Followers"
            } else {
                tab.text = "Following"
            }
        }.attach()


    }

    fun isFavoriteUser(login: String, avatarUrl: String?) {
        favoriteViewModel.getFavoriteUsersbyUsername(login).observe(this) { favoriteUser ->
            if (favoriteUser !=  null) {
                binding.fabFavorite.setImageDrawable(resources.getDrawable(R.drawable.baseline_favorite_24))
                binding.fabFavorite.setOnClickListener {
                    favoriteViewModel.delete(favoriteUser)
                    Toast.makeText(
                    this,
                    StringBuilder(login + " ").append(resources.getString(R.string.delete_from_fav)),
                    Toast.LENGTH_SHORT
                )
                    .show()
                }
            } else {
                binding.fabFavorite.setImageDrawable(resources.getDrawable(R.drawable.baseline_favorite_border_24))
                val newFavoriteUser = FavoriteUserEntity(login, avatarUrl)
                binding.fabFavorite.setOnClickListener {
                    favoriteViewModel.insert(newFavoriteUser)
                    Toast.makeText(
                    this,
                    StringBuilder(login + " ").append(resources.getString(R.string.add_to_fav)),
                    Toast.LENGTH_SHORT
                )
                    .show()
                }
            }
        }
    }


    private fun setUserDetail(userDetail: UserDetailResponse) {
        binding.userDetailLogin.text = userDetail.login
        binding.userDetailName.text = userDetail.name ?: " - "
        binding.userFollowerCount.text = userDetail.followers.toString()
        binding.userFollowingCount.text = userDetail.following.toString()
        Glide.with(this)
            .load(userDetail.avatarUrl)
            .into(binding.userDetailAvatar)
    }

    private fun resetUserDetail() {
        binding.userDetailLogin.text = ""
        binding.userDetailName.text = ""
        binding.userFollowerCount.text = ""
        binding.userFollowingCount.text = ""
        Glide.with(this)
            .load(R.drawable.ic_launcher_foreground)
            .into(binding.userDetailAvatar)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDetail.visibility = View.VISIBLE
            resetUserDetail()
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FAV_USER = "extra_fav_user"
    }
}