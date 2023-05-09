package com.example.crypto2023

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.crypto2023.adapter.CoinInfoAdapter
import com.example.crypto2023.pojo.CoinPriceInfo
import com.example.crypto2023.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val viewModel:CoinViewModel by viewModels()
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter= CoinInfoAdapter(requireContext())
        adapter.onCoinClickListener=object: CoinInfoAdapter.OnCoinClickListener{
            override fun OnCoinClick(coinPriceInfo: CoinPriceInfo) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf(SecondFragment.EXTRA_FROM_SYMBOL to coinPriceInfo.fromSymbol))
            }
        }

        binding.recyclerVIewCponPriceList.adapter=adapter
        Log.i("tpop","it.toString()")
        viewModel.priceList.observe(viewLifecycleOwner) {
            adapter.coinInfoList = it
            Log.i("tpop", "База успех")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}