package com.iwmh.spodifyapp.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainRepositoryModule {

    @Provides
    fun provideMainRepository(mainRepositoryImpl: MainRepositoryImpl) : MainRepository {
        return mainRepositoryImpl
    }
}