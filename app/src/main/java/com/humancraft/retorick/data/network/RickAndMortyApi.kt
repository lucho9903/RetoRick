package com.humancraft.retorick.data.network

import com.humancraft.retorick.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterResponse>
}
