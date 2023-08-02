package com.rianjaradev.androidtemplate.poc.domain.interactor

import com.github.michaelbull.result.Result
import com.rianjaradev.androidtemplate.core.exception.CommonError
import com.rianjaradev.androidtemplate.poc.domain.entity.Feature1Entity
import com.rianjaradev.androidtemplate.poc.domain.entity.Feature1Params
import com.rianjaradev.androidtemplate.poc.domain.repository.IFeature1Repository
import javax.inject.Inject

class Feature1UseCase @Inject constructor(
    private val repository: IFeature1Repository,
) {
    suspend fun invoke(params: Feature1Params): Result<Feature1Entity, CommonError> {
        return repository.fetch(params)
    }
}