package com.budajovanovic.android_task.framework.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.budajovanovic.android_task.R
import com.budajovanovic.android_task.databinding.FragmentPostDetailsBinding
import com.budajovanovic.android_task.business.model.User
import com.budajovanovic.android_task.business.state.DataState
import com.budajovanovic.android_task.framework.ui.base.BaseFragment
import com.budajovanovic.android_task.framework.ui.base.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>() {

    private val postDetailsViewModel: PostDetailsViewModel by viewModels()
    private val args: PostDetailsFragmentArgs by navArgs()

    override fun getLayoutId(): Int {
        return R.layout.fragment_post_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            Timber.d("Back")
            findNavController().popBackStack()
        }
        binding.toolbar.title = getString(R.string.post_details).toUpperCase()

        binding.title = args.postTitle
        binding.body = args.postBody

        subscribeObserver()
        postDetailsViewModel.getUser(args.userId)
    }

    private fun subscribeObserver() {
        postDetailsViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Loading -> uiController.showProgressBar()

                is DataState.Success<User> -> {
                    uiController.hideProgressBar()
                    binding.user = dataState.data
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