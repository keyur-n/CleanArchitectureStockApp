package com.example.cleanarchitecturestockapp.presentation.company_info

import com.example.cleanarchitecturestockapp.domain.model.CompanyInfo
import com.example.cleanarchitecturestockapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos:List<IntradayInfo> = emptyList(),
    val company:CompanyInfo?=null,
    val isLoading:Boolean=false,
    val error:String?=null,

)
