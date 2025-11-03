package com.example.a02climov.controller

object AuthController {
    private const val USER = "MONSTER"
    private const val PASS = "MONSTER9"

    fun isValid(user: String, pass: String): Boolean =
        user.trim().equals(USER, ignoreCase = true) &&
                pass == PASS
}
