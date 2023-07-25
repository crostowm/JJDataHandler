package io

import com.example.sales_summary_handler.SalesSummary
import java.io.File
import java.util.Scanner
import java.util.StringTokenizer

class SalesSummaryReader(file: File) {
    private var scanner: Scanner? = null
    val entities = ArrayList<SalesSummary>()

    init {
        val st = StringTokenizer(file.name.substring(0, file.name.length - 4), "_")
        st.nextToken() // Consume the SS
        val week = st.nextToken().toInt()
        val year = st.nextToken().toInt()
        try {
            scanner = Scanner(file)
            while (scanner?.hasNext() == true) {
                var storeNumber = -1
                var totalFreebies = -1.0
                var mgrLaborPerc = -1.0
                var `mgrLabor$` = -1.0
                var inshopLaborPerc = -1.0
                var `inshopLabor$` = -1.0
                var driverLaborPerc = -1.0
                var `driverLabor$` = -1.0
                var taxLaborPerc = -1.0
                var `taxLabor$` = -1.0
                var dmrLaborPerc = -1.0
                var `dmrLabor$` = -1.0
                var totalLaborPerc = -1.0
                var `totalLabor$` = -1.0
                var breadPerc = -1.0
                var foodPerc = -1.0
                var sidesPerc = -1.0
                var paperPerc = -1.0
                var producePerc = -1.0
                var beveragePerc = -1.0
                var operatingPerc = -1.0
                var cateringPerc = -1.0
                var totalCogs = -1.0
                var totalSales = -1.0
                var line = scanner?.nextLine() ?: ""

                // Check for beginning of store
                var consolidatedList = splitButConsolidateSpecial(
                    line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray(), '"')
                if (consolidatedList.size >= 1) if (consolidatedList[0].contains("Entity") && consolidatedList[0].length > 11) {
                    val storeNo = consolidatedList[0].substring(8, 12)
                    storeNumber = storeNo.toInt()
                    // Iterate through 8 lines of each store
                    for (dataCount in 0..7) {
                        var ii = 1
                        while (ii < consolidatedList.size) {
                            val s = consolidatedList[ii]
                            // index 10
                            if (s == "Total Freebies") {
                                ii++
                                totalFreebies = convertToDouble(consolidatedList[ii])
                            }
                            // index 15
                            if (s == "Manager") {
                                ii++
                                mgrLaborPerc = convertToDouble(consolidatedList[ii])
                                ii++
                                `mgrLabor$` = convertToDouble(consolidatedList[ii])
                                ii += 2
                                inshopLaborPerc = convertToDouble(consolidatedList[ii])
                                ii++
                                `inshopLabor$` = convertToDouble(consolidatedList[ii])
                                ii += 2
                                driverLaborPerc = convertToDouble(consolidatedList[ii])
                                ii++
                                `driverLabor$` = convertToDouble(consolidatedList[ii])
                                ii += 2
                                taxLaborPerc = convertToDouble(consolidatedList[ii])
                                ii++
                                `taxLabor$` = convertToDouble(consolidatedList[ii])
                                ii += 2
                                dmrLaborPerc = convertToDouble(consolidatedList[ii])
                                ii++
                                `dmrLabor$` = convertToDouble(consolidatedList[ii])
                                ii += 2
                                totalLaborPerc = convertToDouble(consolidatedList[ii])
                                ii++
                                `totalLabor$` = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Bread 4311") {
                                ii++
                                breadPerc = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Food 4312") {
                                ii++
                                foodPerc = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Sides 4313") {
                                ii++
                                sidesPerc = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Paper 4314") {
                                ii++
                                paperPerc = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Produce 4315") {
                                ii++
                                producePerc = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Beverage 4316") {
                                ii++
                                beveragePerc = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Catering 4320") {
                                ii++
                                cateringPerc = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Operating Supplies 5220") {
                                ii++
                                operatingPerc = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Sub Total COGS") {
                                ii++
                                totalCogs = convertToDouble(consolidatedList[ii])
                            }
                            if (s == "Total Sales") {
                                ii++
                                totalSales = convertToDouble(consolidatedList[ii])
                            }
                            ii++
                        }
                        if (scanner?.hasNext() == true && dataCount != 7) {
                            line = scanner?.nextLine() ?: ""
                            consolidatedList = splitButConsolidateSpecial(
                                line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                                    .toTypedArray(), '"')
                        }
                    }
                }

                // Check to see if entity exists
                if (storeNumber != -1) {
                    val entity = getEntity(storeNumber)
                    if (entity == null) {
                        var ss: SalesSummary? = null
                        ss = SalesSummary(
                            storeNumber,
                            week,
                            year,
                            totalFreebies,
                            mgrLaborPerc,
                            `mgrLabor$`,
                            inshopLaborPerc,
                            `inshopLabor$`,
                            driverLaborPerc,
                            `driverLabor$`,
                            taxLaborPerc,
                            `taxLabor$`,
                            dmrLaborPerc,
                            `dmrLabor$`,
                            totalLaborPerc,
                            `totalLabor$`,
                            breadPerc,
                            foodPerc,
                            sidesPerc,
                            paperPerc,
                            producePerc,
                            beveragePerc,
                            operatingPerc,
                            cateringPerc,
                            totalCogs,
                            totalSales
                        )
                        if (ss != null) {
                            entities.add(ss)
                        }
                    } else {
                        println(entity)
                        println("Failed to read Sales Summary")
                    }
                }
            }
            scanner?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (weekData in entities) {
			println(weekData.toString());
		}
    }

    private fun getEntity(storeNumber: Int): SalesSummary? {
        for (ss in entities) {
            if (ss.storeNumber == storeNumber) return ss
        }
        return null
    }

    private fun convertToDouble(string: String): Double {
        return removePerc(removeDollar(removeQuotes(string))).toDouble()
    }

    private fun removePerc(string: String): String {
        var string = string
        string = string.trim { it <= ' ' }
        return if (string[string.length - 1] == '%') string.substring(0, string.length - 1)
            .trim { it <= ' ' } else string
    }

    private fun removeDollar(string: String): String {
        var string = string
        string = string.trim { it <= ' ' }
        return if (string[0] == '$') string.substring(1).trim { it <= ' ' } else string
    }

    private fun removeQuotes(string: String): String {
        var string = string
        string = string.trim { it <= ' ' }
        return if (string[0] == '"') string.substring(1, string.length - 1)
            .trim { it <= ' ' } else string
    }

    private fun splitButConsolidateSpecial(line: Array<String>, special: Char): ArrayList<String> {
        val tokens = ArrayList<String>()
        var temp = ""
        var ii = 0
        while (ii < line.size) {
            temp = line[ii].trim { it <= ' ' }
            if (temp.length > 0) {
                // Found beginning quote
                if (temp[0] == special) {
                    // Iterate until end quote
                    while (temp[temp.length - 1] != special) {
                        if (ii++ < line.size) {
                            // Add all tokens til end
                            temp += line[ii].trim { it <= ' ' }
                        }
                    }
                }
                tokens.add(temp)
            }
            ii++
        }
        return tokens
    }
}
