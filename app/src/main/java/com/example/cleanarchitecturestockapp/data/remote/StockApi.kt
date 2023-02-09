package com.example.cleanarchitecturestockapp.data.remote

import com.example.cleanarchitecturestockapp.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("/query?function=LISTING_STATUS")
    suspend fun getListing(
        @Query("apikey") apikey: String = STOCK_API_KEY,
    ): ResponseBody

    @GET("/query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntraday(
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = STOCK_API_KEY,
    ): ResponseBody

    @GET("/query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = STOCK_API_KEY,
    ): CompanyInfoDto


    companion object {
        const val STOCK_API_KEY = "4CMS56LBZKFCKJXI"
        const val BASE_URL = "https://www.alphavantage.co/"
    }
}