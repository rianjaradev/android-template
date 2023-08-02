package com.rianjaradev.androidtemplate.poc.data.model

import com.rianjaradev.androidtemplate.poc.domain.entity.Feature1Entity

data class Feature1Model(
    val data: String,
)

data class Feature1Response(
    val feature: Feature1Model,
)

fun Feature1Response.toEntity() = Feature1Entity(data = this.feature.data)