package com.example.cleanarchitecturestockapp.domain.model

import java.time.LocalDateTime
import java.util.*

data class IntradayInfo(
    val dateTime: LocalDateTime,
    val close: Double
)