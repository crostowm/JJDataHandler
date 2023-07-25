package com.example.sales_summary_handler

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream

fun readSSJson(inputStream: InputStream): List<SalesSummary> {
    val gson = Gson()
    val content = inputStream.bufferedReader().use(
        BufferedReader::readText)
    val listSalesSummaryType = object : TypeToken<List<SalesSummary>>() {}.type
    val data: List<SalesSummary> = gson.fromJson(content, listSalesSummaryType)
    return data
}