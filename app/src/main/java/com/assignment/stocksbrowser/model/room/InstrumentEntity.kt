package com.assignment.stocksbrowser.model.room

import com.assignment.stocksbrowser.ui.home.screen.InstrumentType

interface InstrumentEntity {
    val symbol: String
    val companyName: String
    val lastSalePrice: Double
    val percentageChange: Double
    fun getType(): InstrumentType
    fun getDetails(): Map<String, String>
}
