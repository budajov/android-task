package com.budajovanovic.android_task.framework.datasource.network.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostNetworkEntity(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("body")
    @Expose
    val body: String,

    @SerializedName("userId")
    @Expose
    val userId: Int
)