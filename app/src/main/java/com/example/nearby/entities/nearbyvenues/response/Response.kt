package com.example.nearby.entities.nearbyvenues.response


data class Response (

	val warning : Warning,
	val suggestedRadius : Int,
	val headerLocation : String,
	val headerFullLocation : String,
	val headerLocationGranularity : String,
	val totalResults : Int,
	val suggestedBounds : SuggestedBounds,
	val groups : List<Groups>
)