package com.example.cleanarchitecturestockapp.presentation.company_list

import com.example.cleanarchitecturestockapp.data.local.CompanyListingEntity

sealed class CompanyListingEvent {
    object Refresh:CompanyListingEvent()
    data class OnSearchQueryChange(val query:String):CompanyListingEvent()
}