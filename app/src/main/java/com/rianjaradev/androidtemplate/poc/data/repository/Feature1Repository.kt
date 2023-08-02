package com.rianjaradev.androidtemplate.poc.data.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.rianjaradev.androidtemplate.core.exception.CommonError
import com.rianjaradev.androidtemplate.core.exception.NetworkError
import com.rianjaradev.androidtemplate.core.network.serviceCallResultWrapper
import com.rianjaradev.androidtemplate.poc.data.model.toEntity
import com.rianjaradev.androidtemplate.poc.data.network.Feature1Service
import com.rianjaradev.androidtemplate.di.IODispatcher
import com.rianjaradev.androidtemplate.poc.domain.entity.Feature1Entity
import com.rianjaradev.androidtemplate.poc.domain.entity.Feature1Params
import com.rianjaradev.androidtemplate.poc.domain.repository.IFeature1Repository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class Feature1Repository @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val service: Feature1Service,
) : IFeature1Repository {
    override suspend fun fetch(params: Feature1Params): Result<Feature1Entity, CommonError> {
        try {
            return serviceCallResultWrapper(dispatcher) {
                service.fetchRetrofit()
            }.map {
                it.toEntity()
            }
        } catch (e: Exception) {
            return Err(NetworkError("Map error"))
        }
    }
}