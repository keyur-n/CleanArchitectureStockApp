package com.example.cleanarchitecturestockapp.data.csv

import android.util.Log
import com.example.cleanarchitecturestockapp.data.mapper.toIntradayInfo
import com.example.cleanarchitecturestockapp.data.remote.dto.IntradayInfoDto
import com.example.cleanarchitecturestockapp.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CsvParser<IntradayInfo> {
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
//        val reader = CSVReader(InputStreamReader(stream))
//        return withContext(Dispatchers.IO) {
//            reader.readAll()
//                .mapNotNull { line ->
//                    val timestamp = line.getOrNull(0)?:return@mapNotNull
//                    val close = line.getOrNull(1)?:return@mapNotNull
//                    val dto = IntradayInfoDto(timestamp = timestamp, close = close)
//                    dto.toIntradayInfo()
//                }
//
//                .also {
//                    reader.close()
//                }
//        }
        val reader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            val list = reader.readAll()
                .drop(1);
            val currentDate = getItem(list[0])?.dateTime?.dayOfMonth ?: LocalDateTime.now()
                .minusDays(0).dayOfMonth
            list.mapNotNull { line ->
                val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                val close = line.getOrNull(4) ?: return@mapNotNull null
                Log.e("ErroCheck", "ChecTimeStamp=$timestamp== CLose=$close")
                val dto = IntradayInfoDto(timestamp = timestamp, close = close.toDouble())
                dto.toIntradayInfo()
            }
                .filter {
                    it.dateTime.dayOfMonth == currentDate
                }
                .sortedBy {
                    it.dateTime.hour
                }
                .also {
                    reader.close()
                }
        }
    }

    private fun getItem(line: Array<String>): IntradayInfo? {
        val timestamp = line.getOrNull(0) ?: return null
        val close = line.getOrNull(4) ?: return null
        Log.e("ErroCheck", "ChecTimeStamp=$timestamp== CLose=$close")
        val dto = IntradayInfoDto(timestamp = timestamp, close = close.toDouble())
        return dto.toIntradayInfo()
    }
}