package com.iwmh.spodifyapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.iwmh.spodifyapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
): ViewModel(){

    // AuthState which this app updates.
    //  (You may delegate some tasks to repositories, but make sure to update this AuthState INSIDE this viewmodel file.)
    private var authState by mutableStateOf(AuthState())

    // State for AppAuth (used when obtaining an authorization code)
    private var state by mutableStateOf("")





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

    fun isAuthorized(): Boolean{
        return authState.isAuthorized
    }


    fun stateValue(): String {
        return state
    }

    fun setStateValue(newValue: String){
        state = newValue
    }

    fun calculateStateValue(){
        val randomStringSource = "abcdefghijklmnopqrstuvwxfzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_.-~"
        var calculatedStateValue = ""
        for(x in 0 .. 127) calculatedStateValue += randomStringSource.random()
        setStateValue(calculatedStateValue)
    }

}
