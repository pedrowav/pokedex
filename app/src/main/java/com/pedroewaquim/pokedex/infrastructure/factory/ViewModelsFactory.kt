package com.pedroewaquim.pokedex.infrastructure.factory

import com.pedroewaquim.pokedex.core.action.GetPokemonList
import com.pedroewaquim.pokedex.presentation.fragment.PokemonListViewModelFactory

object ViewModelsFactory {
    val pokemonListViewModelFactory by lazy {
        PokemonListViewModelFactory(
            GetPokemonList(RetrofitFactory.createPokemonApiService())
        )
    }
}