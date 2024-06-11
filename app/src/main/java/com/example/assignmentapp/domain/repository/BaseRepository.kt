package com.example.assignmentapp.domain.repository

import com.example.assignmentapp.data.remote.model.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

interface BaseRepository {
    suspend fun <T> parseError(response: Response<T>, gson: Gson): ErrorResponse {
        return gson.fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
    }
}