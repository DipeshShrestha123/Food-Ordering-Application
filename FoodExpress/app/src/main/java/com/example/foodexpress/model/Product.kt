package com.example.foodexpress.model

data class Product(
    var productRandomId: String ?= null,
    var productTitle: String ?= null,
    var productQuantity: Int ?= null,
    var productUnit: String ?= null,
    var productPrice:  Int ?= null,
    var productstock:  Int ?= null,
    var productCategory:  String ?= null,
    var productDetails:  String ?= null,
    var itemCount: Int ?= null,
    var adminUid: String ?= null,
    var productimageUris: ArrayList<String?>? = null,
)
