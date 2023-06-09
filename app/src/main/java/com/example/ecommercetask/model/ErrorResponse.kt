package com.example.ecommercetask.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: Jithu Sunny
 * @Date: 07-12-2022
 * Oges Infotech
 */
data class ErrorResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_description")
    val error_description: String
)
