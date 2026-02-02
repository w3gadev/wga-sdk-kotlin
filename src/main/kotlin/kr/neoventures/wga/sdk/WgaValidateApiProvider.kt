package kr.neoventures.wga.sdk

interface WgaValidateApiProvider {
    fun create(baseUrl: String, apiKey: String): WgaValidateApi
}

class DefaultWgaValidateApiProvider : WgaValidateApiProvider {
    override fun create(baseUrl: String, apiKey: String): WgaValidateApi {
        return DefaultWgaValidateApi(baseUrl, apiKey)
    }
}
