package com.pedroewaquim.pokedex.infrastructure.factory

import com.pedroewaquim.pokedex.core.action.GetPokemon
import com.pedroewaquim.pokedex.presentation.fragment.PokemonListViewModelFactory

object ViewModelsFactory {
    val pokemonListViewModelFactory by lazy {
        PokemonListViewModelFactory(
            GetPokemon(RetrofitFactory.createPokemonApiService())
        )
    }
}