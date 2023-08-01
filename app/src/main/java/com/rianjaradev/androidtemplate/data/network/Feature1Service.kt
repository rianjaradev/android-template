package com.rianjaradev.androidtemplate.data.network

import com.rianjaradev.androidtemplate.data.model.Feature1Response
import retrofit2.Response
import retrofit2.http.GET

interface Feature1Service {

    @GET("feature1")
    suspend fun fetchRetrofit(): Response<Feature1Response>

}