package com.example.workbanktest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workbanktest.network.*
import com.example.workbanktest.util.getDateTodayWithoutTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyViewModel : ViewModel() {

    var today = getDateTodayWithoutTime()
    val dateNb = MutableLiveData<String>(today)
    val datePb = MutableLiveData<String>(today)

    val currencyPBData = MutableLiveData<List<NetworkCurrencyExchangeData>>()
    val currencyNBData = MutableLiveData<List<LocalNBCurrency>>()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currencyExchangeData =
                    NetworkPrivateBankApi.currencyService.getCurrency(date = today).exchangeRate
                if (currencyExchangeData != null) {
                    currencyPBData.postValue(currencyExchangeData.filter { it.purchaseRatePB != null })
                    currencyNBData.postValue(currencyExchangeData.filter { it.currency != null }
                        .map {
                            var foreignValue = 1
                            var nationalValue = it.purchaseRateNB!!
                            while (nationalValue < 1) {
                                nationalValue *= 10
                                foreignValue *= 10
                            }
                            LocalNBCurrency(
                                it.currency!!,
                                it.currency.toRussianName(),
                                String.format("%.2fUAH", nationalValue),
                                foreignValue.toString() + it.currency.toString()
                            )
                        })
                }
            }
        }
    }

    fun getCurrencyPB(datePb: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currencyExchangeData =
                    NetworkPrivateBankApi.currencyService.getCurrency(date = datePb).exchangeRate
                if (currencyExchangeData != null) {
                    currencyPBData.postValue(currencyExchangeData.filter { it.purchaseRatePB != null })
                }
            }
        }
    }

    fun getCurrencyNB(dateNb: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currencyExchangeData =
                    NetworkPrivateBankApi.currencyService.getCurrency(date = dateNb).exchangeRate
                if (currencyExchangeData != null) {
                    currencyNBData.postValue(currencyExchangeData.filter { it.currency != null }
                        .map {
                            var foreignValue = 1
                            var nationalValue = it.purchaseRateNB!!
                            while (nationalValue < 1) {
                                nationalValue *= 10
                                foreignValue *= 10
                            }
                            LocalNBCurrency(
                                it.currency!!,
                                it.currency.toRussianName(),
                                String.format("%.2fUAH", nationalValue),
                                foreignValue.toString() + it.currency.toString()
                            )
                        })
                }
            }
        }
    }

    fun getSecondTablePosition(currency: Currency?): Int {
        return currencyNBData.value!!.indexOfFirst { it.currency == currency }
    }
}

