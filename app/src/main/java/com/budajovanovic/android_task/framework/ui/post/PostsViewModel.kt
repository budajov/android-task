package com.budajovanovic.android_task.framework.ui.post

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.budajovanovic.android_task.business.model.Post
import com.budajovanovic.android_task.business.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PostsViewModel
@ViewModelInject
constructor(
    private val postsRepository: PostsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Post>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Post>>>
        get() = _dataState

    fun getPostsRequest(refresh: Boolean) {
        if (refresh) {
            _dataState.value = null
        }
        if (_dataState.value != null) {
            return
        }
        viewModelScope.launch {
            postsRepository.getPosts(refresh).onEach { dataState ->
                _dataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    fun removePostLocally(post: Post) {
        viewModelScope.launch {
            postsRepository.removePostLocally(post).onEach { dataState ->
                _dataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}