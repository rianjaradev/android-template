package com.rianjaradev.androidtemplate.data.model

import com.rianjaradev.androidtemplate.domain.entity.Feature1Entity

data class Feature1Model(
    val data: String,
)

data class Feature1Response(
    val feature: Feature1Model,
)

fun Feature1Response.toEntity() = Feature1Entity(data = this.feature.data)