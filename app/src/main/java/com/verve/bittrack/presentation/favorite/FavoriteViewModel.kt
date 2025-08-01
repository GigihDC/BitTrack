package com.verve.bittrack.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.verve.bittrack.data.model.Favorite
import com.verve.bittrack.data.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repo: FavoriteRepository,
) : ViewModel() {
    fun getAllFavorites() = repo.getUserFavoriteData().asLiveData(Dispatchers.IO)

    fun removeFavorite(item: Favorite) {
        viewModelScope.launch(Dispatchers.IO) { repo.deleteFavorite(item).collect() }
    }
}
