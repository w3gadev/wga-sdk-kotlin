package kr.neoventures.wga.sdk.dto


@Serializable
data class GenerateCodeReq(
    val clientUserId: String
)

@Serializable
data class GenerateCodeRes(
    val code: String
)
