package com.example.nearby.entities.login.response

data class User(
    var email: String = "",
    var id: Int = 0,
    var image: String = "",
    var lang: String = "",
    var mobile: String = "",
    var name: String = "",
    var token: String = ""
)