package com.example.cleanarchitecturestockapp.domain.model

import com.google.gson.annotations.SerializedName

data class CompanyInfo(
    val symbol:String,
    val description:String,
    val name:String,
    val country:String,
    val industry:String,
)