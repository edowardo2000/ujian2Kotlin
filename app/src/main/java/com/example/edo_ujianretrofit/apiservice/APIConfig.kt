package com.example.edo_ujianretrofit.apiservice

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import de.hdodenhof.circleimageview.BuildConfig

object APIConfig {
    class TokenInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request : Request = chain.request().newBuilder()
                .header("X-Api-Key", "90F305BE6704F5B54976B68F89E53EAB")
                .build()
            return chain.proceed(request)

        }
    }

    fun getApiService(): APIServices{
        val loggingInterceptor = if (BuildConfig.DEBUG){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else{
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val tokenInterceptor = TokenInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f76-103-8-185-130.ngrok-free.app/cicool/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(APIServices::class.java)
    }
}
