package com.example.assignmentapp.data.remote.web_service

import com.example.assignmentapp.di.qualifier.ApiServiceQualifier
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    @ApiServiceQualifier
    private val apiService: ApiService
) : ApiHelper {
}