package kr.neoventures.wga.sdk.dto

import kotlinx.serialization.Serializable


@Serializable
data class GenerateCodeReq(
    val clientUserId: String
)

@Serializable
data class GenerateCodeRes(
    val code: String
)
