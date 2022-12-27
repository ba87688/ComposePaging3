package com.example.splashapicompose.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLinks(
    var self:String,
    var html:String,
    var photos:String,
    var likes:String,
    var porfolio:String,
    var following:String,
    var followers:String
)
