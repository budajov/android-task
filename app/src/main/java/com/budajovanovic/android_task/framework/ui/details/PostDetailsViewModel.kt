package com.budajovanovic.android_task.framework.ui.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.budajovanovic.android_task.business.model.User
import com.budajovanovic.android_task.business.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PostDetailsViewModel
@ViewModelInject
constructor(
    private val postDetailsRepository: PostDetailsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<User>> = MutableLiveData()

    val dataState: LiveData<DataState<User>>
        get() = _dataState

    fun getUser(userId: String) {
        if (_dataState.value != null) {
            return
        }
        viewModelScope.launch {
            postDetailsRepository.getUser(userId).onEach { dataState ->
                _dataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}