package com.iwmh.spodifyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.iwmh.spodifyapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
): ViewModel(){

    fun userIsLoggedIn() : Boolean{
        return false
    }

}