package com.rianjaradev.androidtemplate.domain.repository

import com.github.michaelbull.result.Result
import com.rianjaradev.androidtemplate.core.exception.CommonError
import com.rianjaradev.androidtemplate.domain.entity.Feature1Entity
import com.rianjaradev.androidtemplate.domain.entity.Feature1Params

interface IFeature1Repository {
    suspend fun fetch(params: Feature1Params): Result<Feature1Entity, CommonError>
}