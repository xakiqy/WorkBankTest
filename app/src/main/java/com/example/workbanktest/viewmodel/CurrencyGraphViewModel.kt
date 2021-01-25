package com.example.workbanktest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workbanktest.network.Currency
import com.example.workbanktest.network.NetworkCurrencyExchangeData
import com.example.workbanktest.network.NetworkPrivateBankApi
import com.example.workbanktest.util.getWholeWeekDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyGraphViewModel : ViewModel() {

    val currencyData = MutableLiveData<HashMap<String, NetworkCurrencyExchangeData>>()

    init {
        getCurrencyLastWeek()
    }

    fun getCurrencyLastWeek() {
        val weekList = getWholeWeekDate()
        val currencyDataMap = HashMap<String, NetworkCurrencyExchangeData>()
        viewModelScope.launch {
            weekList.forEach {
                withContext(Dispatchers.IO) {
                    val currencyExchangeData =
                        NetworkPrivateBankApi.currencyService.getCurrency(date = it).exchangeRate
                    if (currencyExchangeData != null) {
                        currencyDataMap[it] =
                            currencyExchangeData.first { it.currency == Currency.USD }
                    }
                }
            }
            currencyData.postValue(currencyDataMap)
        }
    }
}