package com.example.voicelockscreen.model

data class DataModelSetting(
    val imageSetting: Int? = null,
    val text: String? = null,
    val imageRound: Int? = null,
    var code: String? = null,
    var status: Boolean? = false
)