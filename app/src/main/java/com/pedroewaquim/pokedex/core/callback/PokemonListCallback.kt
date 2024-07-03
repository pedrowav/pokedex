package com.pedroewaquim.pokedex.core.callback

import com.pedroewaquim.pokedex.core.domain.Pokemon

interface PokemonListCallback {
    operator fun invoke(values: List<Pokemon>)
}