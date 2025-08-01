package com.verve.bittrack.presentation.splash

import androidx.lifecycle.ViewModel
import com.verve.bittrack.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {
    fun isUserLoggedIn() = repository.isLoggedIn()
}
