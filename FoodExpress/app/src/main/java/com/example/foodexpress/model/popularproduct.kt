package com.example.foodexpress.model

data class popularproduct(
    val popularitemname:String ?= null,
    val popularitemimg: Int,
    val popularitemrating:String ?= null,
    val popularitemprice:String ?= null
)
