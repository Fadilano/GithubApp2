package com.example.githubapp2.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp2.R
import com.example.githubapp2.data.remote.response.UserGithubResponse
import com.example.githubapp2.databinding.ActivityMainBinding
import com.example.githubapp2.ui.UserListAdapter
import com.example.githubapp2.ui.detail.DetailActivity
import com.example.githubapp2.ui.favorite.FavoriteActivity
import com.example.githubapp2.ui.setting.SettingActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userListAdapter = UserListAdapter()
        binding.recyclerViewMain.adapter = userListAdapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMain.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerViewMain.addItemDecoration(itemDecoration)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listUser.observe(this) { userList ->
            setDataUser(userList)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.getUsersSearch(searchView.text.toString())
                    false
                }
        }
        binding.searchBar.inflateMenu(R.menu.menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

    }

    private fun setDataUser(usersList: List<UserGithubResponse>?) {
        if (usersList != null) {
            userListAdapter.submitList(usersList)
            userListAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                override fun onItemClicked(user: UserGithubResponse) {
                    sendSelectedUser(user)
                }
            })
        }
    }

    private fun sendSelectedUser(data: UserGithubResponse) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(intent)
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val query = "alex"
    }
}