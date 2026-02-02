package kr.neoventures.wga.sdk

import kotlinx.coroutines.runBlocking
import kr.neoventures.wga.sdk.dto.LinkUserReq
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WgaValidateApiTest {
    lateinit var baseUrl: String
    lateinit var apiKey: String
    lateinit var api: DefaultWgaValidateApi

    @BeforeEach
    fun setUp() {
        baseUrl = System.getenv("WGA_TEST_BASE_URL")
            ?: throw IllegalArgumentException("WGA_TEST_BASE_URL environment variable required")

        apiKey = System.getenv("WGA_TEST_API_KEY")
            ?: throw IllegalArgumentException("WGA_TEST_API_KEY environment variable required")

        api = DefaultWgaValidateApi(baseUrl, apiKey)
    }

    @Test
    fun `WgaValidateApi - linkUser`() {
        val req = LinkUserReq("test-address", "test-client-user-id")

        runBlocking {
            api.linkUser(req)
        }
    }
}
