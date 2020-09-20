package com.budajovanovic.android_task.framework.ui.details

import com.budajovanovic.android_task.DEFAULT_ERROR_MSG
import com.budajovanovic.android_task.framework.datasource.network.services.NetworkService
import com.budajovanovic.android_task.business.model.User
import com.budajovanovic.android_task.business.state.DataState
import com.budajovanovic.android_task.framework.datasource.network.util.NetworkConnectionProvider
import com.budajovanovic.android_task.framework.datasource.network.mappers.UserNetworkEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class PostDetailsRepository
constructor(
    private val networkService: NetworkService,
    private val userNetworkEntityMapper: UserNetworkEntityMapper,
    private val networkConnectionProvider: NetworkConnectionProvider
) {

    suspend fun getUser(userId: String): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            if (networkConnectionProvider.isOnline()) {
                val networkUser = networkService.getUser(userId = userId)
                emit(DataState.Success(userNetworkEntityMapper.mapFromEntity(networkUser)))
            }
        } catch (e: Exception) {
            emit(DataState.Error(DEFAULT_ERROR_MSG, e))
        }
    }

}