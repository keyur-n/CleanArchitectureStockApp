package com.example.cleanarchitecturestockapp.presentation.company_list

import androidx.annotation.BoolRes
import com.example.cleanarchitecturestockapp.domain.model.CompanyListing

data class CompanyListingState(
    val listCompany:List<CompanyListing> = emptyList(),
    val isLoading:Boolean=false,
    val isRefreshing:Boolean=false,
    val searchQuery:String= "",
) {
}