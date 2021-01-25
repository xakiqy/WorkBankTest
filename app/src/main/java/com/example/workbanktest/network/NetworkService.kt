package com.example.workbanktest.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface PrivateBankCurrencyTrackService {
    @GET("p24api/exchange_rates?")
    suspend fun getCurrency(
        @Query(value = "json") json: String = "",
        @Query(value = "date") date: String
    ): NetworkCurrencyData

}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object NetworkPrivateBankApi {
    //API server has bad api calls, so loading will be long.
    var client: OkHttpClient? = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.privatbank.ua/").client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()

    val currencyService = retrofit.create(PrivateBankCurrencyTrackService::class.java)
}

