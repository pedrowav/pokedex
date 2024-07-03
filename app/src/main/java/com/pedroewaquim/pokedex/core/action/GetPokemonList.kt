package com.pedroewaquim.pokedex.core.action

import android.util.Log
import com.pedroewaquim.pokedex.core.callback.PokemonListCallback
import com.pedroewaquim.pokedex.core.domain.Pokemon
import com.pedroewaquim.pokedex.core.response.PokemonListResponse
import com.pedroewaquim.pokedex.core.service.PokemonApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetPokemonList(
    private val service: PokemonApiService
) : Callback<PokemonListResponse> {

    private lateinit var _callback: PokemonListCallback

    operator fun invoke(callback: PokemonListCallback, offset: Int = 0) {
        _callback = callback
        service.getAllPokemon(PAGINATION, offset).enqueue(this)
    }

    override fun onResponse(
        call: Call<PokemonListResponse>,
        response: Response<PokemonListResponse>
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            val results = response.body()?.results ?: emptyList()
            val pokemonList: List<Pokemon> = mapTo(results)
            _callback.success(pokemonList)
        }

        Log.d("RETROFIT", "${response.code()}: ${response.body()}")
    }

    override fun onFailure(call: Call<PokemonListResponse>, throwable: Throwable) {
        _callback.error()
        Log.e("RETROFIT", "Error: ${throwable.message}")
    }

    private fun getPokemonNumberFromUrl(pokemonUrl: String): Int {
        val urlSplit = pokemonUrl.split("/")
        val number = urlSplit[urlSplit.size - 2].toInt()
        return number
    }

    private fun mapTo(oneShotList: List<PokemonListResponse.PokemonOneShotResponse>): List<Pokemon> {
        return oneShotList.map { oneShot ->
            val pokemonNumber = getPokemonNumberFromUrl(oneShot.url)
            val response = service.getPokemon(pokemonNumber).execute()

            if (response.isSuccessful) {
                val pokemon = response.body()!!
                Pokemon(
                    pokemon.id,
                    oneShot.name.replaceFirstChar { first -> first.titlecase() },
                    pokemon.sprites.default
                )
            } else {
                Pokemon(1, oneShot.name.replaceFirstChar { first -> first.titlecase() })
            }
        }
    }

    companion object {
        const val PAGINATION: Int = 20
    }
}