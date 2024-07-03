package com.pedroewaquim.pokedex.core.action

import android.util.Log
import com.pedroewaquim.pokedex.core.callback.PokemonListCallback
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.core.response.PokemonListResponse
import com.pedroewaquim.pokedex.core.service.PokemonApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetPokemonList(private val service: PokemonApiService) {

    operator fun invoke(pokemonCallback: PokemonListCallback, offset: Int = 0) {
        service.getAllPokemon(PAGINATION, offset).enqueue(createCallback(pokemonCallback))
    }

    private fun createCallback(pokemonCallback: PokemonListCallback): Callback<PokemonListResponse> {
        return object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                val pokemonList: List<Pokemon> = mapTo(response.body()?.results)
                pokemonCallback(pokemonList)
                Log.d("RETROFIT", "${response.code()}: ${response.body()}")
            }

            override fun onFailure(call: Call<PokemonListResponse>, throwable: Throwable) {
                Log.e("RETROFIT", "Error: ${throwable.message}")
            }
        }
    }

    private fun mapTo(oneShot: List<PokemonListResponse.PokemonOneShotResponse>?): List<Pokemon> {
        return oneShot?.map { pokemon ->
            Pokemon(
                id = 1,
                name = pokemon.name.replaceFirstChar { first -> first.titlecase() })
        } ?: emptyList()
    }

    companion object {
        const val PAGINATION: Int = 20
    }
}