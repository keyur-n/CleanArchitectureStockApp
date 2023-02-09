package com.example.cleanarchitecturestockapp.data.csv

import java.io.InputStream

interface CsvParser<T> {
    suspend fun parse(stream: InputStream): List<T>
}