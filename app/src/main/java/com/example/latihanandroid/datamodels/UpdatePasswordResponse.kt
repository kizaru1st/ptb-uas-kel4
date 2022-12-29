package com.example.latihanandroid.datamodels

import com.google.gson.annotations.SerializedName

data class UpdatePasswordResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)