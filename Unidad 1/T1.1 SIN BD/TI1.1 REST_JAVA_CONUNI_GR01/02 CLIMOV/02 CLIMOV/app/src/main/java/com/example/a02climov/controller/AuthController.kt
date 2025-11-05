package com.example.a02climov.controller

import com.example.a02climov.model.Session

object AuthController {
    private const val USER = "MONSTER"
    private const val PASS = "MONSTER9"

    fun login(u:String, p:String): Session? =
        if (u == USER && p == PASS) Session(u) else null
}
