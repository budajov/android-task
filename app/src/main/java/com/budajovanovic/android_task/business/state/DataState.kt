package com.budajovanovic.android_task.business.state

import java.lang.Exception

sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val errMsg: String, val exception: Exception) : DataState<Nothing>()
    object NoInternetConnection : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}