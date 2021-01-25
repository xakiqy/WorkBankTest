package com.example.workbanktest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workbanktest.databinding.ListPbCurrencyBinding
import com.example.workbanktest.network.NetworkCurrencyExchangeData

class CurrencyPBAdapter(val onClickListener: OnClickListener) :
    ListAdapter<NetworkCurrencyExchangeData, CurrencyPBAdapter.CurrencyDataViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyPBAdapter.CurrencyDataViewHolder {
        return CurrencyDataViewHolder(
            ListPbCurrencyBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(
        holder: CurrencyPBAdapter.CurrencyDataViewHolder,
        position: Int
    ) {
        val currencyProperty = getItem(position)
        holder.bind(currencyProperty)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(currencyProperty)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NetworkCurrencyExchangeData>() {
        override fun areItemsTheSame(
            oldItem: NetworkCurrencyExchangeData,
            newItem: NetworkCurrencyExchangeData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: NetworkCurrencyExchangeData,
            newItem: NetworkCurrencyExchangeData
        ): Boolean {
            return oldItem.currency == newItem.currency
        }
    }

    class CurrencyDataViewHolder(private var binding: ListPbCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyProperty: NetworkCurrencyExchangeData) {
            binding.property = currencyProperty
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (currencyProperty: NetworkCurrencyExchangeData) -> Unit) {
        fun onClick(currencyProperty: NetworkCurrencyExchangeData) = clickListener(currencyProperty)
    }
}