package com.example.cleanarchitecturestockapp.data.mapper

import com.example.cleanarchitecturestockapp.data.local.CompanyListingEntity
import com.example.cleanarchitecturestockapp.data.remote.dto.CompanyInfoDto
import com.example.cleanarchitecturestockapp.data.remote.dto.IntradayInfoDto
import com.example.cleanarchitecturestockapp.domain.model.CompanyInfo
import com.example.cleanarchitecturestockapp.domain.model.CompanyListing
import com.example.cleanarchitecturestockapp.domain.model.IntradayInfo
import java.text.SimpleDateFormat
import java.util.*

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}


fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
//    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
//    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return CompanyInfo(
        symbol = symbol ?: "",
        name = name ?: "",
        description = description ?: "",
        industry = industry ?: "",
        country = country ?: "",
    )
}