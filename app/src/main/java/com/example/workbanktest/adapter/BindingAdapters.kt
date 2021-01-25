package com.example.workbanktest.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workbanktest.network.LocalNBCurrency
import com.example.workbanktest.network.NetworkCurrencyExchangeData


@BindingAdapter("listCurrencyPB")
fun bindCurrencyPBRecyclerView(
    recyclerView: RecyclerView,
    data: List<NetworkCurrencyExchangeData>?
) {
    val adapter = recyclerView.adapter as CurrencyPBAdapter
    adapter.submitList(data)
}

@BindingAdapter("listCurrencyNB")
fun bindCurrencyNBRecyclerView(
    recyclerView: RecyclerView,
    data: List<LocalNBCurrency>?
) {
    val adapter = recyclerView.adapter as CurrencyNBAdapter
    adapter.submitList(data)
}
