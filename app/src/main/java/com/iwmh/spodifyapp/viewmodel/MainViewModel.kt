package com.iwmh.spodifyapp.viewmodel

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.iwmh.spodifyapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
): ViewModel(){

    /***
     * Fields
     */
    // AuthState which this app updates.
    //  (You may delegate some tasks to repositories, but make sure to update this AuthState INSIDE this viewmodel file.)
    private var authState by mutableStateOf(AuthState())

    // State for AppAuth (used when obtaining an authorization code)
    private var state by mutableStateOf("")

    // AppAuth's AuthorizationService
    @Inject lateinit var authService: AuthorizationService

    // Dispose authService when ViewModel clears (and when Activity destroys).
    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }

    /***
     * Methods
     */

    /***
     * --- for "authState"
     */
    // Update AuthState with AuthorizationResponse/AuthorizationException
    fun updateAuthState(authResponse: AuthorizationResponse?, authException: AuthorizationException?){
        authState.update(authResponse, authException)
    }

    // Update AuthState with TokenResponse/AuthorizationException
    fun updateAuthState(tokenResponse: TokenResponse?, authException: AuthorizationException?){
        authState.update(tokenResponse, authException)
    }

    // Set AuthState instance when
    fun setNewAuthState(newAuthState: AuthState){
        authState = newAuthState
    }

    // See if the token is there and valid.
    fun isAuthorized(): Boolean{
        return authState.isAuthorized
    }

    /***
     * --- for "state"
     */
    // Get the "state" value.
    fun stateValue(): String {
        return state
    }

    // Just set the new "state" value.
    fun setStateValue(newValue: String){
        state = newValue
    }

    // Generate the "state" value.
    fun calculateStateValue(){
        val randomStringSource = "abcdefghijklmnopqrstuvwxfzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_.-~"
        var calculatedStateValue = ""
        for(x in 0 .. 127) calculatedStateValue += randomStringSource.random()
        setStateValue(calculatedStateValue)
    }

    /***
     * --- for "authService"
     */
    // Returns AuthorizationIntent.
    fun getAuthorizationRequestIntent(authorizationRequest: AuthorizationRequest): Intent {
        return authService.getAuthorizationRequestIntent(authorizationRequest)
    }

    // Exchange AuthorizationCode with AccessToken/RefreshToken.
    fun exchangeAuthorizationCode(resp: AuthorizationResponse){

        // Exchange the authorization code
        authService.performTokenRequest(
            resp.createTokenExchangeRequest()
        ) { tokenResp, tokenEx ->
            // Token exchange failed, check ex for detail.
            if (tokenResp == null) {
                throw  Exception("Token exchange failed. " + tokenEx.toString())
            }
            // Token exchange succeeded.
            // Update the AuthState
            updateAuthState(tokenResp, tokenEx)

            // go on...

        }
    }

}
