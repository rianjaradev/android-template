package com.rianjaradev.androidtemplate.di

import com.google.gson.Gson
import com.rianjaradev.androidtemplate.poc.data.network.Feature1Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl("http://192.168.100.73:3001/")
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        val timeOut = 5L
        return OkHttpClient.Builder().apply {
            addInterceptor(getHttpInterceptor())
            connectTimeout(timeOut, TimeUnit.SECONDS)
            readTimeout(timeOut, TimeUnit.SECONDS)
            writeTimeout(timeOut, TimeUnit.SECONDS)
        }.build()

    }

    private fun getHttpInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): Feature1Service {
        return retrofit.create(Feature1Service::class.java)
    }
}