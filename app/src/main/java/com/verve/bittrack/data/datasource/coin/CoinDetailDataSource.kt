package com.verve.bittrack.data.datasource.coin

import com.verve.bittrack.data.source.network.model.CoinDetailResponse
import com.verve.bittrack.data.source.network.services.ApiServices

interface CoinDetailDataSource {
    suspend fun getCoinDetail(id: String?): CoinDetailResponse
}

class CoinDetailDataSourceImpl(private val service: ApiServices) : CoinDetailDataSource {
    override suspend fun getCoinDetail(id: String?): CoinDetailResponse {
        return service.getCoinDetail(id = id)
    }
}
