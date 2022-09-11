package com.example.voicelockscreen.model

data class PatternPoint(
    val x: Float = 0.0f,
    val y: Float = 0.0f,
    var r: Float = 0.0f, //radius
    val pattern: String = ""
) {
    var isSelect = false
}