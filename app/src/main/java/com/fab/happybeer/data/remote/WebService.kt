package com.fab.happybeer.data.remote

import com.fab.happybeer.BuildConfig
import com.fab.happybeer.core.AppConstants
import com.fab.happybeer.data.model.Beer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WebService {

    @GET("beers")
    suspend fun getBeerList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Beer>
}

object RetrofitClient {
    val webService by lazy {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(logging)
        }

        Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}