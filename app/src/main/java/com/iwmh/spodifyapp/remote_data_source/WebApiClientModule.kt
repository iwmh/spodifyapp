package com.iwmh.spodifyapp.remote_data_source

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.iwmh.spodifyapp.repository.model.Secret
import com.iwmh.spodifyapp.util.Util
import com.iwmh.spodifyapp.view.SpodifyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.openid.appauth.*
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