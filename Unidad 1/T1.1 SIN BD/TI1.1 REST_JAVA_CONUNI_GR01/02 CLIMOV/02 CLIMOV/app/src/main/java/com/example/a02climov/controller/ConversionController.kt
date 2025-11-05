package com.example.a02climov.controller

import com.example.a02climov.model.ConversionType
import com.example.a02climov.service.RestClient

class ConversionController(private val api: RestClient = RestClient()) {

    fun setBaseUrl(url: String) { api.baseUrl = url }

    suspend fun convertir(type: ConversionType, valor: Double): Double = when (type) {
        ConversionType.KM_A_MILLAS -> api.kmToMi(valor)
        ConversionType.M_A_PIES    -> api.mToFt(valor)
        ConversionType.IN_A_CM     -> api.inToCm(valor)
        ConversionType.KG_A_LB     -> api.kgToLb(valor)
        ConversionType.G_A_OZ      -> api.gToOz(valor)
        ConversionType.LB_A_KG     -> api.lbToKg(valor)
        ConversionType.C_A_F       -> api.cToF(valor)
        ConversionType.F_A_C       -> api.fToC(valor)
        ConversionType.C_A_K       -> api.cToK(valor)
    }
}
