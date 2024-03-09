package com.myothiha.domain.model

data class Cast(
    val id: Int,
    val originalName: String,
    val profilePath: String
)
data class Credit(
    val cast: List<Cast>,
    val crew: List<Cast>,
    val id: Int
)