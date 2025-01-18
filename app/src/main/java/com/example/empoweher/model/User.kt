package com.example.empoweher.model

data class User(
    var name : String?=null,
    var userID : String?=null,
    var Dp:String?=null,
    var followers:List<String>?=null,
    var following:List<String>?=null
)
