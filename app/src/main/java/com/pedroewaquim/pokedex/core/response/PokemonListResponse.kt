package com.pedroewaquim.pokedex.core.response

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val results: List<PokemonOneShotResponse>
) {

    data class PokemonOneShotResponse(
        val name: String,
        val url: String
    )
}