package com.pedroewaquim.pokedex.core.service

import com.pedroewaquim.pokedex.core.response.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    fun getAllPokemon(@Query("limit") limit: Int, @Query("offset") offset: Int) : Call<PokemonListResponse>
}