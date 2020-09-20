package com.budajovanovic.android_task.framework.ui.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.budajovanovic.android_task.R
import com.budajovanovic.android_task.business.model.Post
import com.budajovanovic.android_task.business.state.DataState
import com.budajovanovic.android_task.databinding.FragmentPostsBinding
import com.budajovanovic.android_task.framework.ui.adapter.PostAdapter
import com.budajovanovic.android_task.framework.ui.base.BaseFragment
import com.budajovanovic.android_task.framework.ui.base.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding>() {

    private val postsViewModel: PostsViewModel by viewModels()
    private val posts: MutableList<Post> = ArrayList<Post>()
    private val postAdapter: PostAdapter = PostAdapter(posts)

    override fun getLayoutId(): Int {
        return R.layout.fragment_posts
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPosts.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rvPosts.adapter = postAdapter
        postAdapter.onItemClick = { post ->
            val direction = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(
                postTitle = post.title,
                postBody = post.body,
                userId = post.userId.toString()
            )
            view.findNavController().navigate(direction)
        }
        postAdapter.onLongItemClick = { post ->
            postsViewModel.removePostLocally(post)
        }

        binding.toolbar.setNavigationOnClickListener {
            Timber.d("Close App")
            uiController.closeApp()
        }
        binding.toolbar.title = getString(R.string.posts).toUpperCase()

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.actionRefresh -> {
                    Timber.d("Refresh Posts")
                    postsViewModel.getPostsRequest(true)
                    posts.clear()
                    postAdapter.notifyDataSetChanged()
                    true
                }
                else -> false
            }
        }

        setHasOptionsMenu(true)
        subscribeObserver()
        postsViewModel.getPostsRequest(false)
    }

    private fun subscribeObserver() {
        postsViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Loading -> uiController.showProgressBar()

                is DataState.Success<List<Post>> -> {
                    uiController.hideProgressBar()
                    posts.clear()
                    posts.addAll(dataState.data)
                    postAdapter.notifyDataSetChanged()
                }

                is DataState.Error -> {
                    uiController.hideProgressBar()
                    showToast(dataState.errMsg)
                }

                is DataState.NoInternetConnection -> {
                    uiController.hideProgressBar()
                    showToast(getString(R.string.check_internet_connection))
                }
            }
        })
    }

}