package kr.neoventures.wga.sdk.dto

import kr.neoventures.wga.sdk.domain.verificationeventproperty.*

@Serializable
data class PropertyDto(
    val mission: MissionProperty? = null,
    val social: SocialProperty? = null,
    val game: GameProperty? = null,
    val asset: AssetProperty? = null,
    val device: DeviceProperty? = null
)

@Serializable
data class MissionProperty(
    val missionId: String?,
    val rewards: String?,
    val expiredTimestamp: String?,
    val detail: String?,
    val failedReasons: String?
)

@Serializable
data class SocialProperty(
    val contentId: String?,
    val contentType: SocialContentType?,
    val parentContentId: String?,
    val textLength: Int?,
    val mediaCount: Int?
)

@Serializable
data class GameProperty(
    val playDurationSec: Int?,
    val success: Boolean?,
    val score: Long?
)

@Serializable
data class AssetProperty(
    val type: AssetType?,
    val name: String?,
    val amount: String?,
    val actionType: AssetActionType,
    val isCash: Boolean?
)

@Serializable
data class DeviceProperty(
    val os: String?,
    val osVersion: String?,
    val deviceModel: String?,
    val networkType: DeviceNetworkType?
)
