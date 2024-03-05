package com.myothiha.domain.utils.extension

fun Int?.orZero() = this ?: 0
fun Long?.orZero() = this ?: 0L
fun Double?.orZero() = this ?: 0.0
fun Boolean?.orTrue() = this ?: true
fun Boolean?.orFalse() = this ?: false