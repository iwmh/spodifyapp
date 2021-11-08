package com.iwmh.spodifyapp.remote_data_source

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iwmh.spodifyapp.repository.model.api.ItemShow
import com.iwmh.spodifyapp.repository.model.api.PagingObject
import com.iwmh.spodifyapp.util.InjectableConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WebApiClientImpl @Inject constructor(
    private val injectableConstants: InjectableConstants,
    private val authStateManager: AuthStateManager,
    private val okHttpClient: OkHttpClient,
    private val gson: Gson
) : WebApiClient {

    // Make sure to call this before every API call.
    override suspend fun refreshTokensIfNecessary(): String{
        return suspendCoroutine { continuation ->

            authStateManager.authState.performActionWithFreshTokens(
                authStateManager.authService
            ) { _, _, ex ->

                // Token refresh failed.
                if (ex != null) {
                    continuation.resumeWithException(ex)
                }

                // Sync authState in storage with the updated authstate.
                authStateManager.updateAuthStateInStorage()
                continuation.resume(authStateManager.authState.accessToken!!)
            }
        }
    }

    // Get User's Saved Shows
    override suspend fun getUsersSavedShows(): PagingObject<ItemShow> {

        val url = injectableConstants.baseUrl + "/me/shows";

        // Make a request to API endpoint.
        val request = Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer ${authStateManager.authState.accessToken}")
            .build()

        return withContext(Dispatchers.IO) {

            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception(response.toString())
            }

            val tokenType = object : TypeToken<PagingObject<ItemShow>>() {}.type
            var respString = response.body?.string()

            gson.fromJson(
                respString,
                tokenType
            )
        }
    }

}