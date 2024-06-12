package com.example.assignmentapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.assignmentapp.BuildConfig
import com.example.assignmentapp.data.constants.ACCESS_TOKEN
import com.example.assignmentapp.data.constants.APP_SHARED_PREF
import com.example.assignmentapp.data.remote.web_service.ApiHelper
import com.example.assignmentapp.data.remote.web_service.ApiHelperImpl
import com.example.assignmentapp.data.remote.web_service.ApiService
import com.example.assignmentapp.db.dao.TaskDao
import com.example.assignmentapp.db.database.AppDatabase
import com.example.assignmentapp.di.qualifier.ApiBaseUrl
import com.example.assignmentapp.di.qualifier.ApiHelperQualifier
import com.example.assignmentapp.di.qualifier.ApiServiceQualifier
import com.example.assignmentapp.di.qualifier.AppPreference
import com.example.assignmentapp.di.qualifier.AuthInterceptor
import com.example.assignmentapp.di.qualifier.BaseUrl
import com.example.assignmentapp.di.qualifier.CommonInterceptor
import com.example.assignmentapp.di.qualifier.PackageName
import com.example.assignmentapp.di.qualifier.ResponseInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @AppPreference
    fun provideAppSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREF, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String =
        if (BuildConfig.DEBUG) "https://api.openweathermap.org/data/2.5/" else "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    @ApiBaseUrl
    fun provideApiBaseUrl(@BaseUrl baseUrl: String): String {
        return "${baseUrl}"
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Singleton
    @Provides
    @AuthInterceptor
    fun provideAuthInterceptor(
        @AppPreference sharedPreferences: SharedPreferences
    ): Interceptor {
        return Interceptor { chain ->
            val token = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
            val request = chain.request()
            val authRequest = request.newBuilder()
                .addHeader("Authorization", token)
                .build()
            chain.proceed(authRequest)
        }
    }

    @Singleton
    @Provides
    @CommonInterceptor
    fun provideCommonInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val commonRequest = request.newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(commonRequest)
        }
    }

    @Singleton
    @Provides
    @ResponseInterceptor
    fun provideResponseInterceptor(
        @ApplicationContext context: Context,
        @AppPreference appSharedPreferences: SharedPreferences
    ): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code == 401) {
                handleUnAuthResponse(context, appSharedPreferences)
            }
            response
        }
    }

    private fun handleUnAuthResponse(context: Context, appSharedPreferences: SharedPreferences) {
        appSharedPreferences.edit {
            clear()
        }
        /*val i = Intent(context, RoutingActivity::class.java)
        i.putExtras(
            bundleOf(
                "message" to "You are currently logged out. Please login to proceed."
            )
        )
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(i)*/
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @AuthInterceptor authInterceptor: Interceptor,
        @CommonInterceptor commonInterceptor: Interceptor,
        @ResponseInterceptor responseInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG)
                addInterceptor(loggingInterceptor)
            addInterceptor(authInterceptor)
            addInterceptor(commonInterceptor)
            addInterceptor(responseInterceptor)
            callTimeout(600, TimeUnit.SECONDS)
            readTimeout(600, TimeUnit.SECONDS)
            connectTimeout(10000, TimeUnit.SECONDS)
        }.build()

    }


    @Provides
    @Singleton
    fun provideRetrofit(
        @ApiBaseUrl apiBaseUrl: String,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(apiBaseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    @Provides
    @Singleton
    @ApiServiceQualifier
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    @ApiHelperQualifier
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper = apiHelperImpl

    @Provides
    @Singleton
    @PackageName
    fun providePackageName(): String {
        return BuildConfig.APPLICATION_ID
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.initialize(context)
    }

    @Provides
    @Singleton
    fun provideBannerDao(db: AppDatabase): TaskDao =
        db.getTaskDao()
}