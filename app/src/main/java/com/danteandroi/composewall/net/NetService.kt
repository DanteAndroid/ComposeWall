package com.danteandroi.composewall.net

import android.annotation.SuppressLint
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/**
 * @author Dante
 * 2019-08-20
 */
class NetService private constructor(val url: String = "") {

    companion object {
        @Volatile
        private var instance: NetService? = null

        fun getInstance(url: String = "") =
            instance ?: synchronized(this) {
                instance ?: NetService(url).also { instance = it }
            }
    }

    var netApi: Any? = null

    inline fun <reified T> getApi(): T {
        if (netApi == null) {
            synchronized(this::class.java) {
                if (netApi == null) {
                    netApi = createRetrofit(url).create(T::class.java)
                }
            }
        }
        return netApi!! as T
    }

    inline fun <reified T> createApi(baseUrl: String = url, okHttpClient: OkHttpClient? = null): T {
        if (okHttpClient == null) return createRetrofit(baseUrl).create(T::class.java)
        return createRetrofit(baseUrl, okHttpClient).create(T::class.java)
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun createRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient = createOkHttpClient()
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    fun getBasicOkHttpClient(): OkHttpClient.Builder {
        val loggingInterceptor = HttpLoggingInterceptor()
            .apply {
                level = if (com.danteandroi.composewall.BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
                else HttpLoggingInterceptor.Level.NONE
            }

        val unsafeTrustManager = createUnsafeTrustManager()
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(unsafeTrustManager), null)
        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, unsafeTrustManager)
            .hostnameVerifier { _, _ -> true }
            .addInterceptor(loggingInterceptor)
    }

    @SuppressLint("CustomX509TrustManager")
    fun createUnsafeTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<out X509Certificate> {
                return emptyArray()
            }
        }
    }

    private fun createOkHttpClient(): OkHttpClient {
        return getBasicOkHttpClient().build()
    }

}