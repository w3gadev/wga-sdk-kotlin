package kr.neoventures.wga.sdk

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kr.neoventures.wga.sdk.dto.GenerateCodeReq
import kr.neoventures.wga.sdk.dto.GenerateCodeRes
import kr.neoventures.wga.sdk.dto.LinkUserReq
import kr.neoventures.wga.sdk.dto.RegisterVerificationEventReq
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class WgaApiException(val code: Int, message: String) : IOException("[$code] $message")

class WgaValidateApiClient(
    private val baseUrl: String,
    private val apiKey: String,
    private val client: OkHttpClient = SHARED_CLIENT
) {

    suspend fun generateCode(request: GenerateCodeReq): GenerateCodeRes {
        val httpRequest = createRequest(
            pathSegments = arrayOf("api", "users", "generate-code"),
            body = request
        )

        return executeRequest(httpRequest) { responseBody ->
            if (responseBody.isBlank()) {
                throw IOException("Server returned empty body for generateCode")
            }

            SHARED_JSON.decodeFromString<GenerateCodeRes>(responseBody)
        }
    }

    suspend fun registerVerificationEvent(request: RegisterVerificationEventReq) {
        val httpRequest = createRequest(
            pathSegments = arrayOf("api", "verification-events"),
            body = request
        )

        executeRequest(httpRequest) { }
    }

    suspend fun linkUser(request: LinkUserReq) {
        val httpRequest = createRequest(
            pathSegments = arrayOf("api", "verification-events", "link"),
            body = request
        )
        executeRequest(httpRequest) { }
    }

    private inline fun <reified T> createRequest(pathSegments: Array<String>, body: T): Request {
        val urlBuilder = baseUrl.toHttpUrlOrNull()?.newBuilder()
            ?: throw IllegalArgumentException("Invalid base URL: $baseUrl")

        pathSegments.forEach { urlBuilder.addPathSegment(it) }

        val requestBody = SHARED_JSON.encodeToString(body).toRequestBody(JSON_MEDIA_TYPE)

        return Request.Builder()
            .url(urlBuilder.build())
            .header("X-WGA-API-Key", apiKey)
            .post(requestBody)
            .build()
    }

    private suspend fun <T> executeRequest(request: Request, mapper: (String) -> T): T {
        val response = client.newCall(request).await()

        return response.use { resp ->
            if (!resp.isSuccessful) {
                val errorBody = resp.body?.string() ?: ""
                throw WgaApiException(resp.code, errorBody)
            }

            val bodyString = resp.body?.string() ?: ""

            try {
                if (resp.code == 204) {
                    mapper("")
                } else {
                    mapper(bodyString)
                }
            } catch (e: SerializationException) {
                throw IOException("Failed to parse JSON response: ${e.message}", e)
            }
        }
    }

    private suspend fun Call.await(): Response {
        return suspendCancellableCoroutine { continuation ->
            enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    if (continuation.isCancelled) return
                    continuation.resumeWithException(e)
                }
            })

            continuation.invokeOnCancellation {
                try { cancel() } catch (ex: Throwable) {}
            }
        }
    }

    companion object {
        private val SHARED_CLIENT = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        private val SHARED_JSON = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }

        private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
    }
}
