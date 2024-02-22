package com.utad.pmdmu5.data.network

import com.utad.pmdmu5.data.network.model.GamesModelItem
import retrofit2.Response
import retrofit2.http.GET

interface GamesService {

    @GET("deals?storeID=1") // TODO cambiar por el endpoint
    suspend fun baseCall(): Response<List<GamesModelItem>>
}

