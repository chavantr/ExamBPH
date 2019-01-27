package com.mywings.blindpeopleassistant.models

data class Product(
    var id: Int = 0,
    var name: String = "",
    var measurement: String = "",
    var quantity: String = "",
    var productInfo: String = "",
    var manufactureDate: String = "",
    var expiryDate: String = "",
    var price: String = ""
)
