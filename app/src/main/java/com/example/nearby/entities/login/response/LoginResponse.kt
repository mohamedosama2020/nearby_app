package com.example.nearby.entities.login.response

data class LoginResponse(
    var msg: String = "",
    var status: String = "",
    var user: User = User()
)