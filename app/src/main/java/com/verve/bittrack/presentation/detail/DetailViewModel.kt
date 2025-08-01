package com.verve.bittrack.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.verve.bittrack.data.model.Coin
import com.verve.bittrack.data.repository.CoinDetailRepository
import com.verve.bittrack.data.repository.FavoriteRepository
import com.verve.bittrack.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailViewModel(
    extras: Bundle?,
    private val coinDetailRepository: CoinDetailRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {
    private val product = extras?.getParcelable<Coin>(DetailActivity.EXTRAS_ITEM_ACT)

    fun getDetailInfo() = coinDetailRepository.getCoinDetail(product?.id).asLiveData(Dispatchers.IO)

    fun addToFavorite(): LiveData<ResultWrapper<Boolean>> {
        return product?.let {
            favoriteRepository.createFavorite(it).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Catalog not found"))) }
    }

    fun removeFavorite(): LiveData<ResultWrapper<Boolean>> {
        return product?.let {
            favoriteRepository.undoFavorite(it).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Coin not Found"))) }
    }
}
