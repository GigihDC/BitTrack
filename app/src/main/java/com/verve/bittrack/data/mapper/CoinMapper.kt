package com.verve.bittrack.data.mapper

import com.verve.bittrack.data.model.Coin
import com.verve.bittrack.data.source.network.model.CoinResponse

fun CoinResponse.toCoin() =
    Coin(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        desc = this.desc.orEmpty(),
        image = this.image.orEmpty(),
        price = this.price ?: 0.0,
    )

fun Collection<CoinResponse>.toCoin() =
    this.map {
        it.toCoin()
    }
