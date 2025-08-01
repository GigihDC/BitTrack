package com.verve.bittrack.data.datasource.favorite

import com.verve.bittrack.data.source.dao.FavoriteDao
import com.verve.bittrack.data.source.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteDataSource {
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    suspend fun insertFavorite(favorite: FavoriteEntity): Long

    suspend fun deleteFavorite(favorite: FavoriteEntity): Int

    suspend fun deleteAll()
}

class FavoriteDatabaseDataSource(
    private val dao: FavoriteDao,
) : FavoriteDataSource {
    override fun getAllFavorites(): Flow<List<FavoriteEntity>> = dao.getAllFavorites()

    override suspend fun insertFavorite(favorite: FavoriteEntity): Long = dao.insertFavorite(favorite)

    override suspend fun deleteFavorite(favorite: FavoriteEntity): Int = dao.deleteFavorite(favorite)

    override suspend fun deleteAll() = dao.deleteAll()
}
