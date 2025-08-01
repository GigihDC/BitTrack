package com.verve.bittrack.data.repository

import com.verve.bittrack.data.datasource.favorite.FavoriteDataSource
import com.verve.bittrack.data.mapper.toFavoriteEntity
import com.verve.bittrack.data.mapper.toFavoriteList
import com.verve.bittrack.data.model.Coin
import com.verve.bittrack.data.model.Favorite
import com.verve.bittrack.data.source.entity.FavoriteEntity
import com.verve.bittrack.utils.ResultWrapper
import com.verve.bittrack.utils.proceed
import com.verve.bittrack.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface FavoriteRepository {
    fun getUserFavoriteData(): Flow<ResultWrapper<Pair<List<Favorite>, Double>>>

    fun createFavorite(catalog: Coin): Flow<ResultWrapper<Boolean>>

    fun deleteFavorite(item: Favorite): Flow<ResultWrapper<Boolean>>

    fun undoFavorite(item: Coin): Flow<ResultWrapper<Boolean>>

    fun deleteAll(): Flow<ResultWrapper<Boolean>>
}

class FavoriteRepositoryImpl(private val favoriteDataSource: FavoriteDataSource) :
    FavoriteRepository {
    override fun getUserFavoriteData(): Flow<ResultWrapper<Pair<List<Favorite>, Double>>> {
        return favoriteDataSource.getAllFavorites()
            .map {
                // mapping into Favorite list and sum the total price
                proceed {
                    val result = it.toFavoriteList()
                    val totalPrice = result.sumOf { it.catalogPrice }
                    Pair(result, totalPrice)
                }
            }.map {
                // map to check when list is empty
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(1000)
            }
    }

    override fun deleteFavorite(item: Favorite): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { favoriteDataSource.deleteFavorite(item.toFavoriteEntity()) > 0 }
    }

    override fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            favoriteDataSource.deleteAll()
            true
        }
    }

    override fun createFavorite(catalog: Coin): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val affectedRow =
                favoriteDataSource.insertFavorite(
                    FavoriteEntity(
                        catalogId = catalog.id,
                        catalogName = catalog.name,
                        catalogImgUrl = catalog.image,
                        catalogPrice = catalog.price,
                    ),
                )
            delay(2000)
            affectedRow > 0
        }
    }

    override fun undoFavorite(item: Coin): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val affectedRow =
                favoriteDataSource.deleteFavorite(
                    FavoriteEntity(
                        catalogId = item.id,
                        catalogName = item.name,
                        catalogImgUrl = item.image,
                        catalogPrice = item.price,
                    ),
                )
            delay(2000)
            affectedRow > 0
        }
    }
}
