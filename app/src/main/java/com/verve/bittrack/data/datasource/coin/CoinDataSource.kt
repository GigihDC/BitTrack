package com.verve.bittrack.data.datasource.coin

import com.verve.bittrack.data.source.network.model.CoinResponse
import com.verve.bittrack.data.source.network.services.ApiServices

interface CoinDataSource {
    suspend fun getCoinList(): List<CoinResponse>
}

class CoinDataSourceImpl(private val service: ApiServices) : CoinDataSource {
    override suspend fun getCoinList(): List<CoinResponse> {
        return service.getCoinList()
    }
}
