package com.iwmh.spodifyapp.util

import android.net.Uri
import androidx.compose.runtime.Composable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

data class InjectableConstants(
    val baseUrl: String
)

@Module
@InstallIn(SingletonComponent::class)
class InjectableConstantsModule{

    @Provides
    @Singleton
    fun provide(): InjectableConstants {
        return InjectableConstants(
            baseUrl =  "https://api.spotify.com/v1"
        )
    }

}