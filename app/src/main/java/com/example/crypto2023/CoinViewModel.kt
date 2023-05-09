package com.example.crypto2023

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.crypto2023.api.ApiFactory
import com.example.crypto2023.database.AppDatabase
import com.example.crypto2023.pojo.CoinPriceInfo
import com.example.crypto2023.repositories.CoinRepository

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val coinRepository= CoinRepository(
        ApiFactory.apiService,
        AppDatabase.getInstance(application).coinPriceInfoDao())
    val priceList: LiveData<List<CoinPriceInfo>> = coinRepository.getPriceList()

    init {
        coinRepository.loadData()
    }

    fun getDetailInfo(fSym:String): LiveData<CoinPriceInfo>{
        return coinRepository.getDetailInfo(fSym)

    }


    override fun onCleared() {
        super.onCleared()
        coinRepository.job.cancel()
    }
}