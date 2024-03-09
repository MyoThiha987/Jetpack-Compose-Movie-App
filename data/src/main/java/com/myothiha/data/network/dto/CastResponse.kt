package com.myothiha.data.network.dto


import com.myothiha.domain.model.Cast
import com.myothiha.domain.model.Credit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDto(
    @SerialName("id")
    val id: Int,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("profile_path")
    val profilePath: String? = null
)

@Serializable
data class CreditResponse(
    @SerialName("cast")
    val cast: List<CastDto>,
    @SerialName("crew")
    val crew: List<CastDto>,
    @SerialName("id")
    val id: Int
)

fun CreditResponse.toDomain(): Credit {
    this.apply {
        return Credit(cast = cast.map { it.toDomain() }, crew = crew.map { it.toDomain() }, id = id)
    }
}

fun CastDto.toDomain(): Cast {
    this.apply {
        return Cast(id = id, originalName = originalName, profilePath = profilePath.orEmpty())
    }
}