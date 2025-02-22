package com.example.empoweher.model

data class User(
    var name : String?=null,
    var userID : String?=null,
    var Dp:String?=null,
    var isEnt:String?=null,
    var interests:MutableList<String>?=null,
    var price:String?="500"
)
