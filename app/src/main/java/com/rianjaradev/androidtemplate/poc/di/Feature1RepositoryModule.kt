package com.rianjaradev.androidtemplate.poc.di

import com.rianjaradev.androidtemplate.poc.data.repository.Feature1Repository
import com.rianjaradev.androidtemplate.poc.domain.repository.IFeature1Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class Feature1RepositoryModule {

    @Binds
    abstract fun bindAssessmentRepository(repository: Feature1Repository): IFeature1Repository
}