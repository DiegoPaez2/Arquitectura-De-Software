package com.example.a02climov.model

enum class ConversionType(val id: Int, val label: String) {
    KM_A_MILLAS(1, "Kilómetros — Millas"),
    M_A_PIES(2, "Metros — Pies"),
    IN_A_CM(3, "Pulgadas — Centímetros"),
    KG_A_LB(4, "Kilogramos — Libras"),
    G_A_OZ(5, "Gramos — Onzas"),
    LB_A_KG(6, "Libras — Kilogramos"),
    C_A_F(7, "Celsius — Fahrenheit"),
    F_A_C(8, "Fahrenheit — Celsius"),
    C_A_K(9, "Celsius — Kelvin");

    companion object {
        fun byPosition(pos: Int): ConversionType = values()[pos]
    }
}
