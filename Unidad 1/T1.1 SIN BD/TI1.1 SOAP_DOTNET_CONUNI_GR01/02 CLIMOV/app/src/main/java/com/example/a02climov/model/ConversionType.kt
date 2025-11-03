package com.example.a02climov.model

enum class ConversionType(val display: String, val method: String, val param: String) {
    KM_A_MILLAS("Kilómetros → Millas", "KilometrosAMillas", "km"),
    M_A_PIES("Metros → Pies", "MetrosAPies", "m"),
    IN_A_CM("Pulgadas → Centímetros", "PulgadasACentimetros", "p"),

    KG_A_LB("Kilogramos → Libras", "KilogramosALibras", "kg"),
    G_A_OZ("Gramos → Onzas", "GramosAOnzas", "g"),
    LB_A_KG("Libras → Kilogramos", "LibrasAKilogramos", "lb"),

    C_A_F("Celsius → Fahrenheit", "CelsiusAFahrenheit", "c"),
    F_A_C("Fahrenheit → Celsius", "FahrenheitACelsius", "f"),
    C_A_K("Celsius → Kelvin", "CelsiusAKelvin", "c");

    override fun toString() = display
}
