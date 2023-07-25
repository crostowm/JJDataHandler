package com.example.sales_summary_handler

data class SalesSummary(
    val storeNumber: Int,
    val week: Int,
    val year: Int,
    val totalFreebies: Double,
    val mgrLaborPerc: Double,
    val mgrLaborDollars: Double,
    val inshopLaborPerc: Double,
    val inshopLaborDollars: Double,
    val driverLaborPerc: Double,
    val driverLaborDollars: Double,
    val taxLaborPerc: Double,
    val taxLaborDollars: Double,
    val dmrLaborPerc: Double,
    val dmrLaborDollars: Double,
    val totalLaborPerc: Double,
    val totalLaborDollars: Double,
    val breadPerc: Double,
    val foodPerc: Double,
    val sidesPerc: Double,
    val paperPerc: Double,
    val producePerc: Double,
    val beveragePerc: Double,
    val operatingPerc: Double,
    val cateringPerc: Double,
    val totalCogs: Double,
    val totalSales: Double
)
