package kr.neoventures.wga.sdk.dto

import kotlinx.serialization.Serializable
import kr.neoventures.wga.sdk.domain.verificationevent.VerificationActionType
import kr.neoventures.wga.sdk.domain.verificationevent.VerificationEventType
import kr.neoventures.wga.sdk.domain.verificationevent.VerificationPlatformType

@Serializable
data class RegisterVerificationEventReq(
    val verificationEventType: VerificationEventType,
    val verificationPlatformType: VerificationPlatformType,
    val verificationActionType: VerificationActionType,
    val clientTimestamp: String,
    val clientUserId: String,
    val ip: String?,
    val userAgent: String?,
    val country: String?,
    val property: PropertyDto?
)

@Serializable
data class LinkUserReq(
    val address: String,
    val clientUserId: String
)
