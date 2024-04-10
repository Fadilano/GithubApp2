package com.example.githubapp2.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp2.data.remote.response.UserGithubResponse
import com.example.githubapp2.databinding.FragmentFollowBinding
import com.example.githubapp2.ui.UserListAdapter


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.recyclerViewFollow.addItemDecoration(itemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = requireArguments().getInt(ARG_POSITION)
        val username = requireArguments().getString(ARG_USERNAME)
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        if (position == 1) {
            detailViewModel.followerList.observe(viewLifecycleOwner) {
                setUserFollowerData(it)
            }
            detailViewModel.isFollowLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            detailViewModel.followingList.observe(viewLifecycleOwner) {
                setUserFollowingData(it)
            }
            detailViewModel.isFollowLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.noFollow.visibility = View.GONE
            binding.progressBarFollow.visibility = View.VISIBLE
            binding.recyclerViewFollow.alpha = 0.0F
        } else {
            binding.progressBarFollow.visibility = View.GONE
            binding.recyclerViewFollow.alpha = 1F
        }
    }

    private fun setUserFollowerData(data: List<UserGithubResponse>) {
        binding.noFollow.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerViewFollow.apply {
            binding.recyclerViewFollow.layoutManager = LinearLayoutManager(context)
            val listUserAdapter = UserListAdapter()
            adapter = listUserAdapter
            listUserAdapter.submitList(data)
            listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserGithubResponse) {
                    val detailViewModel =
                        ViewModelProvider(requireActivity())[DetailViewModel::class.java]
                    detailViewModel.username = data.login
                }
            })
        }
    }

    private fun setUserFollowingData(data: List<UserGithubResponse>) {
        binding.noFollow.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerViewFollow.apply {
            binding.recyclerViewFollow.layoutManager = LinearLayoutManager(context)
            val listUserAdapter = UserListAdapter()
            adapter = listUserAdapter
            listUserAdapter.submitList(data)
            listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserGithubResponse) {
                    val detailViewModel =
                        ViewModelProvider(requireActivity())[DetailViewModel::class.java]
                    detailViewModel.username = data.login
                }
            })
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}