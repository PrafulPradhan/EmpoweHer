package com.example.empoweher.model

data class JsonUser(
    var userID: String?=null,
    var education: Int? =0,
    var safety: Int? =0,
    var empowerment: Int? =0,
    var dailyGuidance: Int? =0,
    var arts: Int? =0,
    var technical: Int? =0,
    var socialAffairs: Int? =0,
    var childProblems: Int? =0,
    var astrology: Int? =0,
    var health: Int? =0,
    var spiritual: Int? =0,
    var history: Int? =0,
    var sports: Int? =0,
    var politics: Int? =0,
    var exploratory: Int? =0,
    var realEstate: Int? =0,
    var business: Int? =0,
    var price: Int? =0,
)

data class JsonUserEvent(
    var eventId:String?=null,
    var price: Int?=0,
    var rating:Double?=0.0,
    var eventDomain:Int?=0
)
