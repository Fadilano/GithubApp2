package com.example.githubapp2.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp2.data.local.database.FavoriteUserEntity
import com.example.githubapp2.data.remote.response.UserGithubResponse
import com.example.githubapp2.databinding.ActivityFavoriteBinding
import com.example.githubapp2.ui.UserListAdapter
import com.example.githubapp2.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerViewFavorite.addItemDecoration(itemDecoration)

        favoriteViewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(application)
        )[FavoriteViewModel::class.java]

        favoriteViewModel.getAllFavoriteUsers().observe(this) {users ->
            val items = arrayListOf<UserGithubResponse>()
            users.map {
                val item = UserGithubResponse(login = it.login, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            val listUserAdapter = UserListAdapter()
            binding.recyclerViewFavorite.adapter = listUserAdapter
            listUserAdapter.submitList(items)
            listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
              override fun onItemClicked(data: UserGithubResponse) {
                  sendSelectedUser(data)
              }
            })
        }
    }


    private fun sendSelectedUser(data: UserGithubResponse) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(intent)
    }
}