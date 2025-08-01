package com.verve.bittrack.data.repository

import com.verve.bittrack.data.datasource.coin.CoinDetailDataSource
import com.verve.bittrack.data.mapper.toCoinDetail
import com.verve.bittrack.data.model.CoinDetail
import com.verve.bittrack.utils.ResultWrapper
import com.verve.bittrack.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CoinDetailRepository {
    fun getCoinDetail(id: String?): Flow<ResultWrapper<CoinDetail>>
}

class CoinDetailRepositoryImpl(
    private val dataSource: CoinDetailDataSource,
) : CoinDetailRepository {
    override fun getCoinDetail(id: String?): Flow<ResultWrapper<CoinDetail>> {
        return proceedFlow { dataSource.getCoinDetail(id = id).toCoinDetail() }
    }
}
