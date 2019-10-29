package com.example.nearby.entities.nearby.response

import Location


data class Venue (

    val id : String,
    val name : String,
    val location : Location,
    val categories : List<Categories>,
    val popularityByGeo : Double,
    val venuePage : VenuePage
)