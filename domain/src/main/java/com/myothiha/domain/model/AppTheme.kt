package com.myothiha.domain.model

data class User(
    val theme: Theme
)

enum class Theme {
    SYSTEM, LIGHT, DARK
}
