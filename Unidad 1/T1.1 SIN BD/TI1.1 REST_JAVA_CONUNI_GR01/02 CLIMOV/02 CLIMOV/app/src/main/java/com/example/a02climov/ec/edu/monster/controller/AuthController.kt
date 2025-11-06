package com.example.a02climov.ec.edu.monster.controller

import com.example.a02climov.ec.edu.monster.model.Session

object AuthController {
    private const val USER = "MONSTER"
    private const val PASS = "MONSTER9"

    fun login(u:String, p:String): Session? =
        if (u == USER && p == PASS) Session(u) else null
}
