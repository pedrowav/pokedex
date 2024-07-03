package com.pedroewaquim.pokedex.infrastructure.factory

import com.pedroewaquim.pokedex.core.service.PokemonApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {
    private const val API_BASE_URL = "https://pokeapi.co/api/v2/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createPokemonApiService() : PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }
}