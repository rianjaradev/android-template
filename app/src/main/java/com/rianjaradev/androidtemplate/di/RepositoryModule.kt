package com.rianjaradev.androidtemplate.di

import com.rianjaradev.androidtemplate.data.repository.Feature1Repository
import com.rianjaradev.androidtemplate.domain.repository.IFeature1Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindAssessmentRepository(repository: Feature1Repository): IFeature1Repository
}