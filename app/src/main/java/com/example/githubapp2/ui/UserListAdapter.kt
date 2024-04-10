package com.example.githubapp2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp2.data.remote.response.UserGithubResponse
import com.example.githubapp2.databinding.ItemUserBinding

class UserListAdapter :
    ListAdapter<UserGithubResponse, UserListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        onItemClickCallback = callback
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserGithubResponse) {
            binding.userLogin.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.userAvatar)
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserGithubResponse>() {
            override fun areItemsTheSame(
                oldItem: UserGithubResponse,
                newItem: UserGithubResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserGithubResponse,
                newItem: UserGithubResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserGithubResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

}