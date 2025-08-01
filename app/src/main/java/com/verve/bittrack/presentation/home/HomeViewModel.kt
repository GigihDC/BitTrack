package com.verve.bittrack.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.verve.bittrack.data.repository.CoinRepository
import com.verve.bittrack.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val coinRepository: CoinRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    fun getCoinList() = coinRepository.getCoinList().asLiveData(Dispatchers.IO)

    fun getCurrentUser() = userRepository.getCurrentUser()
}
