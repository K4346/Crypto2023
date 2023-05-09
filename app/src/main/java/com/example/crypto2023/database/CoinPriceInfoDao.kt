package com.example.crypto2023.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crypto2023.pojo.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC") // DESC - по убыванию
    fun getPriceList(): LiveData<List<CoinPriceInfo>> // берем список наших объектов коинпрайсинфо

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1") // берем из таблицы 1 объект коинпрайсинфо. Так как ищет все совпадения, то ставим лимит на всякий
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)//сохраняем полученные из инета данные в базу (онконфликт риплейс для замены старых данных новыми)
    fun insertPriceList(priceList: List<CoinPriceInfo>)//получаем список объектов
}