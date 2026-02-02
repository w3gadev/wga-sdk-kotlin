package kr.neoventures.wga.sdk.exception

import java.io.IOException

class WgaApiException(val code: Int, message: String) : IOException("[$code] $message")
