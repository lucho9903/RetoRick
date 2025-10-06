package com.humancraft.retorick.data.repository

import com.humancraft.retorick.data.model.CharacterResponse
import com.humancraft.retorick.data.network.RickAndMortyApi
import retrofit2.Response

class CharacterRepository(private val api: RickAndMortyApi) {
    suspend fun getCharacters(page: Int): Response<CharacterResponse> {
        return api.getCharacters(page)
    }
}
