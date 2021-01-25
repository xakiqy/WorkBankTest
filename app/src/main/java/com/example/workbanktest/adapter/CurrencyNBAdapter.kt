package com.example.workbanktest.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workbanktest.databinding.ListNbCurrencyBinding
import com.example.workbanktest.network.LocalNBCurrency

class CurrencyNBAdapter(val onClickListener: OnClickListener) :
    ListAdapter<LocalNBCurrency, CurrencyNBAdapter.CurrencyDataViewHolder>(
        DiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyNBAdapter.CurrencyDataViewHolder {
        return CurrencyDataViewHolder(
            ListNbCurrencyBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(
        holder: CurrencyNBAdapter.CurrencyDataViewHolder,
        position: Int
    ) {
        val currencyProperty = getItem(position)
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#3988B1A5"))
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }
        holder.bind(currencyProperty)
        holder.itemView.tag = currencyProperty.currency
        holder.itemView.setOnClickListener {
            onClickListener.onClick(currencyProperty)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<LocalNBCurrency>() {
        override fun areItemsTheSame(
            oldItem: LocalNBCurrency,
            newItem: LocalNBCurrency
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: LocalNBCurrency,
            newItem: LocalNBCurrency
        ): Boolean {
            return oldItem.currency == newItem.currency
        }
    }

    class CurrencyDataViewHolder(private var binding: ListNbCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyProperty: LocalNBCurrency) {
            binding.property = currencyProperty
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (currencyProperty: LocalNBCurrency) -> Unit) {
        fun onClick(currencyProperty: LocalNBCurrency) = clickListener(currencyProperty)
    }
}