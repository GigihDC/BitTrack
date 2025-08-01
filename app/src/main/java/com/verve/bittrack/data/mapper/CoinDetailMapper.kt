package com.verve.bittrack.data.mapper

import com.verve.bittrack.data.model.CoinDetail
import com.verve.bittrack.data.source.network.model.CoinDetailResponse

fun CoinDetailResponse.toCoinDetail() =
    CoinDetail(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        symbol = this.symbol.orEmpty(),
        image = this.image?.large.orEmpty(),
        desc = this.description?.en.orEmpty(),
        price = this.market?.price?.usd ?: 0.0,
    )
