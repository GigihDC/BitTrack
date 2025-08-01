package com.verve.bittrack.data.mapper

import com.verve.bittrack.data.model.Favorite
import com.verve.bittrack.data.source.entity.FavoriteEntity

fun Favorite?.toFavoriteEntity() =
    FavoriteEntity(
        catalogId = this?.catalogId.orEmpty(),
        catalogName = this?.catalogName.orEmpty(),
        catalogPrice = this?.catalogPrice ?: 0.0,
        catalogImgUrl = this?.catalogImgUrl.orEmpty(),
    )

fun FavoriteEntity?.toFavorite() =
    Favorite(
        catalogId = this?.catalogId.orEmpty(),
        catalogName = this?.catalogName.orEmpty(),
        catalogPrice = this?.catalogPrice ?: 0.0,
        catalogImgUrl = this?.catalogImgUrl.orEmpty(),
    )

fun List<FavoriteEntity?>.toFavoriteList() = this.map { it.toFavorite() }
