package com.example.a02climov.controller

import com.example.a02climov.service.SoapClient

enum class ConversionType(val method: String, val param: String) {
    KM_A_MILLAS("KilometrosAMillas", "km"),
    M_A_PIES("MetrosAPies", "m"),
    PULGADAS_A_CM("PulgadasACentimetros", "p"),

    KG_A_LB("KilogramosALibras", "kg"),
    G_A_OZ("GramosAOnzas", "g"),
    LB_A_KG("LibrasAKilogramos", "lb"),

    C_A_F("CelsiusAFahrenheit", "c"),
    F_A_C("FahrenheitACelsius", "f"),
    C_A_K("CelsiusAKelvin", "c");
}

object ConversionController {
    fun convert(type: ConversionType, value: Double): Double {
        return SoapClient.callDoubleMethod(type.method, type.param, value)
    }
}
