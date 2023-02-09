package com.example.cleanarchitecturestockapp.di

import com.example.cleanarchitecturestockapp.data.csv.CompanyListingParser
import com.example.cleanarchitecturestockapp.data.csv.CsvParser
import com.example.cleanarchitecturestockapp.data.csv.IntradayInfoParser
import com.example.cleanarchitecturestockapp.data.repository.StockRepositoryImpl
import com.example.cleanarchitecturestockapp.domain.model.CompanyListing
import com.example.cleanarchitecturestockapp.domain.model.IntradayInfo
import com.example.cleanarchitecturestockapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsCompanyListingParser(
        companyListingParser:CompanyListingParser
    ):CsvParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindsIntradayInfoParser(
        companyListingParser:IntradayInfoParser
    ):CsvParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl):StockRepository
}