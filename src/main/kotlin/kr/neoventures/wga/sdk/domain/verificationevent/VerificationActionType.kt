package kr.neoventures.wga.sdk.domain.verificationevent

enum class VerificationActionType {

    SIGN_UP,
    WITHDRAW,
    LOG_IN,
    LOG_OUT,

    MISSION_ACCEPT,
    MISSION_CLEAR,
    MISSION_FAIL,

    POST_CREATE,
    POST_UPDATE,
    POST_DELETE,

    COMMENT_CREATE,
    COMMENT_UPDATE,
    COMMENT_DELETE,

    LIKE_CREATE,
    LIKE_DELETE,

    ASSET_INFLOW,
    ASSET_OUTFLOW,

    SESSION_START,
    SESSION_END,

    STAGE_CLEAR,
    STAGE_FAIL
}
