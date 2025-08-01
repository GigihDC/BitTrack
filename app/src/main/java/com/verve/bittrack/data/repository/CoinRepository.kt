package com.verve.bittrack.data.repository

import com.verve.bittrack.data.datasource.coin.CoinDataSource
import com.verve.bittrack.data.mapper.toCoin
import com.verve.bittrack.data.model.Coin
import com.verve.bittrack.utils.ResultWrapper
import com.verve.bittrack.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinList(): Flow<ResultWrapper<List<Coin>>>
}

class CoinRepositoryImpl(private val dataSource: CoinDataSource) : CoinRepository {
    override fun getCoinList(): Flow<ResultWrapper<List<Coin>>> {
        return proceedFlow { dataSource.getCoinList().toCoin() }
    }
}
