package com.example.cleanarchitecturestockapp.domain.repository

import com.example.cleanarchitecturestockapp.domain.model.CompanyInfo
import com.example.cleanarchitecturestockapp.domain.model.CompanyListing
import com.example.cleanarchitecturestockapp.domain.model.IntradayInfo
import com.example.cleanarchitecturestockapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListing(
        fetchFromRemote:Boolean,
        query:String,
    ):Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol:String,
    ):Resource<List<IntradayInfo>>


    suspend fun getCompanyInfo(
        symbol:String,
    ):Resource<CompanyInfo>
}