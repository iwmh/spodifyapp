package com.iwmh.spodifyapp.remote_data_source

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebApiClientModule @Inject constructor(
) {
    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context): WebApiClient {
            return WebApiClientImpl(context)
    }
}