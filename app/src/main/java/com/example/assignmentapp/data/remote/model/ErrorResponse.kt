package com.example.assignmentapp.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.JsonElement
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Keep
@Parcelize
data class ErrorResponse(
    val cod: Int,
    val message: String? = "Something went wrong ! Please try again later.",
    val errors: @RawValue JsonElement? = null
) : Parcelable
