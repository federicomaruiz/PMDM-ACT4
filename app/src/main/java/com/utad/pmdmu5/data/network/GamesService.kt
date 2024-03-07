package com.utad.pmdmu5.data.network

import com.utad.pmdmu5.data.network.model.GamesModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesService {
    @GET("deals")
    suspend fun baseCall(@Query("storeID") storeId: Int): Response<List<GamesModelItem>>

}

