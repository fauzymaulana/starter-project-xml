package com.papero.capstoneexpert.core.data.networking

import com.papero.capstoneexpert.core.BuildConfig
import com.papero.capstoneexpert.core.utilities.libraries.C
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * KlikAdzkiSiapHadapiCPNS / com.klik.adzkia.networking
 * Created by Fauzi Maulana (papero.mint@gmail.com)
 * On 14:56, Okt 31, 2022
 */

class RequestClient @Inject constructor() {
    private fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val tlsEcdsa = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
        val tlsRsa = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
        val tlsDhe = CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(tlsEcdsa, tlsRsa, tlsDhe)
            .build()

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val reqBuilder = request.newBuilder()
                reqBuilder.header("Accept", C.HEADER_APP_JSON)
                    .header("Content-Type", C.HEADER_APP_JSON)
                    .header("Content-Type", C.HEADER_APP_JSON)
                    .header("Authorization", "Bearer ${BuildConfig.TOKEN}")

                val response = chain.proceed(reqBuilder.build())
                return@addInterceptor response.newBuilder().build()
            }

            .addInterceptor(interceptor)
            .connectionSpecs(listOf(spec, ConnectionSpec.CLEARTEXT))
            .connectTimeout(BuildConfig.TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(BuildConfig.TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(BuildConfig.TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    fun getClient(baseUrl: String = BuildConfig.BASE_URL): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getHttpClient())
            .build()
    }
}