package com.example.ecommercetask.model

data class ProductResponseModel(
    val status: String? = null,
    val message: String? = null,
    val data: Data? = null
)
data class Data (
    val id: String,
    val sku: String,
    val name: String,
    val attributeSetID: String,
    val price: String,
    val finalPrice: String,
    val brandName: String,
    val image: String,
    val description: String
)
