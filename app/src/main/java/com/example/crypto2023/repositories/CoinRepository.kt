package com.example.crypto2023.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.crypto2023.api.ApiFactory
import com.example.crypto2023.api.ApiService
import com.example.crypto2023.database.CoinPriceInfoDao
import com.example.crypto2023.pojo.CoinPriceInfo
import com.example.crypto2023.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import kotlinx.coroutines.*

class CoinRepository(private val apiService: ApiService, private val coinPriceInfoDao: CoinPriceInfoDao) {
    var job = Job()


    fun getPriceList(): LiveData<List<CoinPriceInfo>> {
        return coinPriceInfoDao.getPriceList()
    }

    fun getDetailInfo(fSym:String): LiveData<CoinPriceInfo>{
        return coinPriceInfoDao.getPriceInfoAboutCoin(fSym)
    }

    private suspend fun getTopCoinsInfo(): String? {
        val response = apiService.getTopCoinsInfo()
        return response.body()?.data?.map { it.coinInfo?.name }?.joinToString(",")
    }

    private suspend fun getPriceListFromNet(): List<CoinPriceInfo> {

            return getTopCoinsInfo()?.let {
                ApiFactory.apiService.getFullPriceList(fSyms = it)
            }?.body().let {getPriceListFromRawData(it)  }
    }


    fun loadData() {
       CoroutineScope(Dispatchers.IO+job).launch{
            while (true){
                coinPriceInfoDao.insertPriceList(getPriceListFromNet())
                delay(10000)
            }
        }

    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData?): List<CoinPriceInfo> {  // Разбиваем json на составляющие
        val jsonObject = coinPriceInfoRawData?.coinPriceInfoJsonObject
        val result = arrayListOf<CoinPriceInfo>()
        if (jsonObject == null) return result

        val coinKeySet = jsonObject.keySet()
        coinKeySet.forEach {coinKey->
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            currencyKeySet.forEach {currencyKey->
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }
}