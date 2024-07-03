package com.pedroewaquim.pokedex.core.callback

import com.pedroewaquim.pokedex.core.domain.Pokemon

interface PokemonListCallback {
    fun success(values: List<Pokemon>)
    fun error()
}