package com.utad.pmdmu5.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GamesApi {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cheapshark.com/api/1.0/") // TODO poner el URL de la api acaba en /
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: GamesService by lazy {
        retrofit.create(GamesService::class.java)
    }


}
