package com.iwmh.spodifyapp.remote_data_source

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.*
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class AppAuthAuthorizationServiceModule @Inject constructor(
) {
    @Provides
    fun provide(@ApplicationContext context: Context): AuthorizationService{
            return AuthorizationService(context)
    }
}