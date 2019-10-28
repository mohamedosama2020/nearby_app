package com.example.nearby.entities.login.send

data class LoginSend(
    var device_id: String = "",
    var mobile: String = "",
    var password: String = "",
    var platform: String = "",
    var version: String = ""
)