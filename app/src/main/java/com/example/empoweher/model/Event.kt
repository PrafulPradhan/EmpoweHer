package com.example.empoweher.model


data class Event (
    var eventId : String?=null,
    var eventName : String?=null,
    var description: String?=null,
    var address:String?=null,
    var city:String?=null,
    var startDate:String?=null,
    var endDate:String?=null,
    var timing:String?=null,
    var duration:String?=null,
    var tag:String?=null,
    var eventImage:String?=null,
    var eventCost:String?=null,
    var capacity:String?=null,
    var vacancy:String?=null,
    var contact:String?=null,
    var hostId:String?=null,
    var meetingId:String?=null
)