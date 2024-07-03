package com.pedroewaquim.pokedex.presentation

import com.pedroewaquim.pokedex.core.domain.Pokemon

sealed class PokedexState {
    data class Success(val pokemonList: List<Pokemon>) : PokedexState()
}