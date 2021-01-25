package com.example.workbanktest.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkCurrencyData(
    @Json(name = "date") val date: String?,
    @Json(name = "bank") val bank: String?,
    @Json(name = "baseCurrencyLit") val baseCurrencyLit: Currency?,
    @Json(name = "exchangeRate") val exchangeRate: List<NetworkCurrencyExchangeData>?
)

@JsonClass(generateAdapter = true)
data class NetworkCurrencyExchangeData(
    @Json(name = "baseCurrency") val baseCurrency: Currency?,
    @Json(name = "currency") val currency: Currency?,
    @Json(name = "saleRateNB") val saleRateNB: Double?,
    @Json(name = "saleRate") val saleRatePB: Double?,
    @Json(name = "purchaseRateNB") val purchaseRateNB: Double?,
    @Json(name = "purchaseRate") val purchaseRatePB: Double?
)

data class LocalNBCurrency(
    val currency: Currency?,
    val translatedCurrency: String,
    val nationalValue: String,
    val foreignValue: String
)

enum class Currency {
    UAH, USD, RUB, EUR, CAD, CNY, CZK, CHY, CHF, DKK, HUF, ILS, JPY, KZT, MDL, SGD, GEL,
    GBP, BYN, AZN, PLZ, NOK, SEK, UZS, TMT, TRY
}

fun Currency.toRussianName(): String {
    return when (this) {
        Currency.UAH -> "Украинская гривна"
        Currency.USD -> "Доллар США"
        Currency.RUB -> "Российский рубль"
        Currency.EUR -> "Евро"
        Currency.CAD -> "Канадский доллар"
        Currency.CNY -> "Жэньминьби"
        Currency.CZK -> "Чешская крона"
        Currency.CHY -> "Китайский юань"
        Currency.CHF -> "Швейцарский франк"
        Currency.DKK -> "Датская крона"
        Currency.HUF -> "Венгерский форинт"
        Currency.ILS -> "Новый израильский шекель"
        Currency.JPY -> "Японская иена"
        Currency.KZT -> "Казахстанский тенге"
        Currency.MDL -> "Молдавский лей"
        Currency.SGD -> "Сингапурский доллар"
        Currency.GEL -> "Грузинский лари"
        Currency.GBP -> "Фунт стерлингов"
        Currency.BYN -> "Белорусский рубль"
        Currency.AZN -> "Азербайджанский манат"
        Currency.PLZ -> "Польский злотый"
        Currency.NOK -> "Норвежская крона"
        Currency.SEK -> "Шведская крона"
        Currency.UZS -> "Узбекский сум"
        Currency.TMT -> "Туркменский манат"
        Currency.TRY -> "Турецкая лира"
    }
}