package com.example.cleanarchitecturestockapp.data.mapper

import com.example.cleanarchitecturestockapp.data.remote.dto.IntradayInfoDto
import com.example.cleanarchitecturestockapp.domain.model.IntradayInfo
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
//    val simpleDateFormat = SimpleDateFormat(pattern,Locale.getDefault())
//    val dateTime = simpleDateFormat.parse(timestamp)

    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(dateTime = localDateTime!!, close = close)
}


