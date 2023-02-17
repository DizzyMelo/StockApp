package com.dicedev.stockapp.data.mapper

import com.dicedev.stockapp.data.local.CompanyListingEntity
import com.dicedev.stockapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name = name, symbol = symbol, exchange = exchange)
}