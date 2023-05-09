package com.example.crypto2023.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto2023.pojo.CoinPriceInfo
import com.example.crypto2023.R
import com.example.crypto2023.databinding.ItemCoinInfoBinding
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context):RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {
    var coinInfoList:List<CoinPriceInfo> = listOf()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    var onCoinClickListener: OnCoinClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CoinInfoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin= coinInfoList[position]
        with(holder){
            val symbols=context.resources.getString(R.string.app_name_Symbols)
            val lastUpd=context.resources.getString(R.string.app_name_Last_Update)
        tvSymbols.text=String.format(symbols,coin.fromSymbol)
        tvPrice.text=coin.price+"$"
        tvLastUpdate.text= String.format(lastUpd,coin.getFormatedTime())
        Picasso.get().load(coin.getFullImageUrl()).into(holder.ivLogoCoin)
    }
        holder.itemView.setOnClickListener({
            onCoinClickListener?.OnCoinClick(coin)
        })
    }

    override fun getItemCount() = coinInfoList.size
    inner class CoinInfoViewHolder(itemView: ItemCoinInfoBinding): RecyclerView.ViewHolder(itemView.root){
        val ivLogoCoin=itemView.imageView
        val tvSymbols=itemView.tvSymbols
        val tvPrice=itemView.textPrice
        val tvLastUpdate=itemView.textUpdates
    }


    interface OnCoinClickListener{
        fun OnCoinClick(coinPriceInfo: CoinPriceInfo)
    }

}