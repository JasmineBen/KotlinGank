package com.conan.kotlingank.data.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RequestManager {

    val BASE_URL = "http://gank.io/api/"
    val DATE_FORMAT_CONVERT = "yyyy-MM-dd'T'HH:mm:ss"

    private val CONNECT_TIMEOUT: Long = 5
    private val READ_TIMEOUT: Long = 10
    var mApi: GankApi

    init {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()
        val gson = GsonBuilder().setDateFormat(DATE_FORMAT_CONVERT).create()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
        mApi = retrofit.create(GankApi::class.java)
    }

    fun getApi(): GankApi {
        return mApi
    }

}